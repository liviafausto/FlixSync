package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.movie.MovieInputDTO;
import com.flixsync.model.dto.movie.MovieOutputDTO;
import com.flixsync.model.entity.CategoryEntity;
import com.flixsync.utils.DurationUtils;
import com.flixsync.model.entity.MovieEntity;
import com.flixsync.repository.MovieRepository;
import com.flixsync.utils.PageUtils;
import com.flixsync.utils.ServiceLog;
import com.flixsync.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public Page<MovieOutputDTO> findAll(Integer pageNumber, Integer pageSize){
        ServiceLog serviceLog = new ServiceLog("MOVIE-FIND-ALL", "movie");
        serviceLog.start("Find all movies");
        serviceLog.pageRequest(pageNumber, pageSize);

        Pageable pageRequest = PageUtils.getPageRequest(pageNumber, pageSize, Sort.by("id"));
        Page<MovieEntity> movies = movieRepository.findAll(pageRequest);
        Page<MovieOutputDTO> moviesOutput = movies.map(MovieOutputDTO::new);

        serviceLog.pageResponse(movies.getNumberOfElements());
        serviceLog.end();
        return moviesOutput;
    }

    public MovieOutputDTO findById(Integer movieId) throws EntityNotFoundException{
        ServiceLog serviceLog = new ServiceLog("MOVIE-FIND-BY-ID", "movie");
        serviceLog.start("Find a movie by id");

        MovieEntity movie = getMovieById(movieId, serviceLog);
        MovieOutputDTO movieOutput = new MovieOutputDTO(movie);

        serviceLog.end();
        return movieOutput;
    }

    public MovieOutputDTO save(MovieInputDTO movieInput) throws InvalidParameterException {
        ServiceLog serviceLog = new ServiceLog("MOVIE-SAVE", "movie");
        serviceLog.start("Register a movie");
        serviceLog.saveRequest(movieInput.toString());

        if(!StringUtils.valid(movieInput.getName())){
            serviceLog.error("The movie's name was not provided");
            serviceLog.end();
            throw new InvalidParameterException("name: can't be blank");
        }
        if(!StringUtils.valid(movieInput.getDirector())){
            serviceLog.error("The movie's director was not provided");
            serviceLog.end();
            throw new InvalidParameterException("director: can't be blank");
        }

        MovieEntity movie = new MovieEntity(movieInput);
        MovieEntity createdMovie = movieRepository.save(movie);
        MovieOutputDTO createdMovieOutput = new MovieOutputDTO(createdMovie);

        serviceLog.saveResponse(createdMovie.toString());
        serviceLog.end();
        return createdMovieOutput;
    }

    public MovieOutputDTO update(Integer movieId, MovieInputDTO movieInput) throws EntityNotFoundException, InvalidParameterException {
        ServiceLog serviceLog = new ServiceLog("MOVIE-UPDATE", "movie");
        serviceLog.start("Update a movie by id");

        final String NEW_NAME = movieInput.getName();
        final Duration NEW_DURATION = DurationUtils.getDuration(movieInput.getHours(), movieInput.getMinutes());
        final LocalDate NEW_RELEASE_DATE = movieInput.getReleaseDate();
        final String NEW_DIRECTOR = movieInput.getDirector();
        final String NEW_SUMMARY = movieInput.getSummary();

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
        MovieOutputDTO updatedMovieOutput = new MovieOutputDTO(updatedMovie);

        serviceLog.updateResponse(movie.toString());
        serviceLog.end();
        return updatedMovieOutput;
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

    protected MovieEntity addCategory(MovieEntity movie, CategoryEntity category, ServiceLog serviceLog) {
        serviceLog.setElementName("movie");
        serviceLog.info("Adding category '" + category.getName() + "' to movie '" + movie.getName() + "'");

        movie.getCategories().add(category);
        MovieEntity updatedMovie = movieRepository.save(movie);

        serviceLog.updateResponse(updatedMovie.toString());
        return updatedMovie;
    }

    protected MovieEntity removeCategory(MovieEntity movie, CategoryEntity category, ServiceLog serviceLog) {
        serviceLog.setElementName("movie");
        serviceLog.info("Removing category '" + category.getName() + "' from movie '" + movie.getName() + "'");

        movie.getCategories().remove(category);
        MovieEntity updatedMovie = movieRepository.save(movie);

        serviceLog.updateResponse(updatedMovie.toString());
        return updatedMovie;
    }

}
