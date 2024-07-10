package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.movie.MovieInputDTO;
import com.flixsync.model.dto.movie.MovieOutputDTO;
import com.flixsync.model.dto.movie.MovieUpdateInputDTO;
import com.flixsync.model.entity.CategoryEntity;
import com.flixsync.utils.DurationUtils;
import com.flixsync.model.entity.MovieEntity;
import com.flixsync.repository.MovieRepository;
import com.flixsync.utils.ServiceLog;
import com.flixsync.utils.StringUtils;
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
        final Duration NEW_DURATION = DurationUtils.getDuration(movieUpdateInput.getHours(), movieUpdateInput.getMinutes());
        final LocalDate NEW_RELEASE_DATE = movieUpdateInput.getReleaseDate();
        final String NEW_DIRECTOR = movieUpdateInput.getDirector();
        final String NEW_SUMMARY = movieUpdateInput.getSummary();

        // Data validation phase
        if(NEW_NAME == null && NEW_DURATION == null && NEW_RELEASE_DATE == null && NEW_DIRECTOR == null && NEW_SUMMARY == null){
            serviceLog.error("No parameters were provided");
            serviceLog.end();
            throw new InvalidParameterException("At least one parameter has to be provided");
        }

        MovieEntity movie = getMovieById(movieId, serviceLog);
        boolean newDataProvided = false;

        if(StringUtils.valid(NEW_NAME) && !NEW_NAME.equals(movie.getName())){
            serviceLog.updateRequest("name", movie.getId(), movie.getName(), NEW_NAME);
            movie.setName(NEW_NAME);
            newDataProvided = true;
        }
        if(NEW_DURATION != null && !NEW_DURATION.equals(movie.getDuration())){
            serviceLog.updateRequest("duration", movie.getId(), DurationUtils.format(movie.getDuration()), DurationUtils.format(NEW_DURATION));
            movie.setDuration(NEW_DURATION);
            newDataProvided = true;
        }
        if(NEW_RELEASE_DATE != null && !NEW_RELEASE_DATE.equals(movie.getReleaseDate())){
            serviceLog.updateRequest("release date", movie.getId(), movie.getReleaseDate().toString(), NEW_RELEASE_DATE.toString());
            movie.setReleaseDate(NEW_RELEASE_DATE);
            newDataProvided = true;
        }
        if(StringUtils.valid(NEW_DIRECTOR) && !NEW_DIRECTOR.equals(movie.getDirector())){
            serviceLog.updateRequest("director", movie.getId(), movie.getDirector(), NEW_DIRECTOR);
            movie.setDirector(NEW_DIRECTOR);
            newDataProvided = true;
        }
        if(StringUtils.valid(NEW_SUMMARY) && !NEW_SUMMARY.equals(movie.getSummary())){
            serviceLog.updateRequest("summary", movie.getId(), movie.getSummary(), NEW_SUMMARY);
            movie.setSummary(NEW_SUMMARY);
            newDataProvided = true;
        }

        if(!newDataProvided){
            serviceLog.error("No new data was provided, so nothing will be updated");
            serviceLog.end();
            throw new InvalidParameterException("No new data was provided");
        }

        // Updating data
        MovieEntity updatedMovie = movieRepository.save(movie);
        MovieOutputDTO output = new MovieOutputDTO(updatedMovie);

        serviceLog.updateResponse(movie.toString());
        serviceLog.end();
        return output;
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
            throw new InvalidParameterException("This movie already belongs to this category!");
        }

        serviceLog.info("Adding category " + category.getId() + " to movie " + movie.getId());
        movie.getCategories().add(category);
        return movieRepository.save(movie);
    }

    protected MovieEntity removeCategory(Integer movieId, CategoryEntity category, ServiceLog serviceLog) throws EntityNotFoundException, InvalidParameterException {
        MovieEntity movie = getMovieById(movieId, serviceLog);

        if(!movie.getCategories().contains(category)){
            serviceLog.error("Movie " + movie.getId() + " is not part of category " + category.getId());
            serviceLog.end();
            throw new InvalidParameterException("This movie doesn't belong to this category!");
        }

        serviceLog.info("Removing category " + category.getId() + " from movie " + movie.getId());
        movie.getCategories().remove(category);
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

}
