package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.movie.MovieInputDTO;
import com.flixsync.model.dto.movie.MovieOutputDTO;
import com.flixsync.model.dto.movie.MovieUpdateInputDTO;
import com.flixsync.model.entity.CategoryEntity;
import com.flixsync.utils.MovieDuration;
import com.flixsync.model.entity.MovieEntity;
import com.flixsync.repository.MovieRepository;
import com.flixsync.utils.ServiceLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public Page<MovieOutputDTO> findAll(Integer pageNumber, Integer amountPerPage){
        ServiceLog serviceLog = new ServiceLog("MOVIE-FIND-ALL", "movie");
        serviceLog.start("Find all movies");

        serviceLog.pageRequest(pageNumber, amountPerPage);
        Page<MovieEntity> movies = movieRepository.findAll(PageRequest.of(pageNumber, amountPerPage, Sort.by("id")));
        serviceLog.pageResponse(movies.getNumberOfElements());

        serviceLog.end();
        return movies.map(MovieOutputDTO::new);
    }

    public MovieOutputDTO findById(Integer movieId) throws EntityNotFoundException{
        ServiceLog serviceLog = new ServiceLog("MOVIE-FIND-BY-ID", "movie");
        serviceLog.start("Find a movie by id");

        MovieEntity movie = getMovieById(movieId, serviceLog);

        serviceLog.end();
        return new MovieOutputDTO(movie);
    }

    public MovieOutputDTO save(MovieInputDTO movieInput){
        ServiceLog serviceLog = new ServiceLog("MOVIE-SAVE", "movie");
        serviceLog.start("Register a movie");

        serviceLog.saveRequest(movieInput.toString());
        MovieEntity movie = new MovieEntity(movieInput);
        MovieEntity createdMovie = movieRepository.save(movie);
        serviceLog.saveResponse(createdMovie.toString());

        serviceLog.end();
        return new MovieOutputDTO(createdMovie);
    }

    public MovieOutputDTO update(Integer movieId, MovieUpdateInputDTO movieUpdateInput)
            throws EntityNotFoundException, InvalidParameterException {
        ServiceLog serviceLog = new ServiceLog("MOVIE-UPDATE", "movie");
        serviceLog.start("Update a movie by id");

        final String NEW_NAME = movieUpdateInput.getName();
        final Long NEW_HOURS = movieUpdateInput.getHours();
        final Long NEW_MINUTES = movieUpdateInput.getMinutes();
        final LocalDate NEW_RELEASE_DATE = movieUpdateInput.getReleaseDate();
        final String NEW_DIRECTOR = movieUpdateInput.getDirector();
        final String NEW_SUMMARY = movieUpdateInput.getSummary();

        // Data validation phase
        if(NEW_NAME == null && NEW_HOURS == null && NEW_MINUTES == null && NEW_RELEASE_DATE == null && NEW_DIRECTOR == null && NEW_SUMMARY == null){
            serviceLog.error("No parameters were provided");
            serviceLog.end();
            throw new InvalidParameterException("At least one parameter has to be provided!");
        }
        if(NEW_HOURS != null && NEW_MINUTES == null || NEW_HOURS == null && NEW_MINUTES != null){
            serviceLog.error("Movie's duration wasn't correctly informed");
            serviceLog.end();
            throw new InvalidParameterException("Both hours and minutes must be specified to update the movie's duration");
        }

        // Updating data
        MovieEntity movie = getMovieById(movieId, serviceLog);
        final Duration DURATION = movie.getDuration();
        boolean movieUpdated = false;

        if(NEW_NAME != null && !NEW_NAME.equals(movie.getName())){
            movie = updateName(movie, NEW_NAME);
            movieUpdated = true;
        }
        if(NEW_HOURS != null && (!NEW_HOURS.equals(MovieDuration.getHours(DURATION)) || !NEW_MINUTES.equals(MovieDuration.getMinutes(DURATION)))){
            // If 'hours' is present, then 'minutes' is also present (validated on the previous phase)
            movie = updateDuration(movie, MovieDuration.getDuration(NEW_HOURS, NEW_MINUTES));
            movieUpdated = true;
        }
        if(NEW_RELEASE_DATE != null && !NEW_RELEASE_DATE.equals(movie.getReleaseDate())){
            movie = updateReleaseDate(movie, NEW_RELEASE_DATE);
            movieUpdated = true;
        }
        if(NEW_DIRECTOR != null && !NEW_DIRECTOR.equals(movie.getDirector())){
            movie = updateDirector(movie, NEW_DIRECTOR);
            movieUpdated = true;
        }
        if(NEW_SUMMARY != null && !NEW_SUMMARY.equals(movie.getSummary())){
            movie = updateSummary(movie, NEW_SUMMARY);
            movieUpdated = true;
        }

        if(movieUpdated) serviceLog.updateResponse(movie.toString());
        else serviceLog.error("No new data was provided, so nothing was updated");

        serviceLog.end();
        return new MovieOutputDTO(movie);
    }

    public void delete(Integer movieId) throws EntityNotFoundException{
        ServiceLog serviceLog = new ServiceLog("MOVIE-DELETE", "movie");
        serviceLog.start("Remove a movie by id");

        MovieEntity movie = getMovieById(movieId, serviceLog);
        serviceLog.deleteRequest(movie.toString());
        movieRepository.delete(movie);
        serviceLog.deleteResponse(movieId);

        serviceLog.end();
    }

    protected MovieEntity addCategory(Integer movieId, CategoryEntity category, ServiceLog serviceLog) throws EntityNotFoundException, InvalidParameterException {
        MovieEntity movie = getMovieById(movieId, serviceLog);

        if(movie.getCategories().contains(category)){
            serviceLog.error("Movie " + movie.getId() + " is already part of category " + category.getId());
            serviceLog.end();
            throw new InvalidParameterException("The movie already belongs to this category!");
        }

        serviceLog.info("Adding category " + category.getId() + " to movie " + movie.getId());
        movie.getCategories().add(category);
        return movieRepository.save(movie);
    }

    protected MovieEntity getMovieById(Integer movieId, ServiceLog serviceLog) throws EntityNotFoundException{
        serviceLog.setElementName("movie");
        serviceLog.searchRequest(movieId);
        Optional<MovieEntity> movie = movieRepository.findById(movieId);

        if(movie.isEmpty()){
            serviceLog.error("Movie not found");
            serviceLog.end();
            throw new EntityNotFoundException("Movie not found");
        }

        serviceLog.searchResponse(movie.get().toString());
        return movie.get();
    }

    private MovieEntity updateName(MovieEntity movie, String newName){
        ServiceLog serviceLog = new ServiceLog("MOVIE-UPDATE-NAME", "movie");
        serviceLog.updateRequest("name", movie.getId(), movie.getName(), newName);
        movie.setName(newName);
        return movieRepository.save(movie);
    }

    private MovieEntity updateDuration(MovieEntity movie, Duration newDuration){
        ServiceLog serviceLog = new ServiceLog("MOVIE-UPDATE-DURATION", "movie");
        serviceLog.updateRequest("duration", movie.getId(), MovieDuration.format(movie.getDuration()), MovieDuration.format(newDuration));
        movie.setDuration(newDuration);
        return movieRepository.save(movie);
    }

    private MovieEntity updateReleaseDate(MovieEntity movie, LocalDate newReleaseDate){
        ServiceLog serviceLog = new ServiceLog("MOVIE-UPDATE-RELEASE-DATE", "movie");
        serviceLog.updateRequest("release date", movie.getId(), movie.getReleaseDate().toString(), newReleaseDate.toString());
        movie.setReleaseDate(newReleaseDate);
        return movieRepository.save(movie);
    }

    private MovieEntity updateDirector(MovieEntity movie, String newDirector){
        ServiceLog serviceLog = new ServiceLog("MOVIE-UPDATE-DIRECTOR", "movie");
        serviceLog.updateRequest("director", movie.getId(), movie.getDirector(), newDirector);
        movie.setDirector(newDirector);
        return movieRepository.save(movie);
    }

    private MovieEntity updateSummary(MovieEntity movie, String newSummary){
        ServiceLog serviceLog = new ServiceLog("MOVIE-UPDATE-SUMMARY", "movie");
        serviceLog.updateRequest("summary", movie.getId(), movie.getSummary(), newSummary);
        movie.setSummary(newSummary);
        return movieRepository.save(movie);
    }

}
