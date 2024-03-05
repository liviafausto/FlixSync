package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.movie.MovieInputDTO;
import com.flixsync.model.dto.movie.MovieOutputDTO;
import com.flixsync.model.entity.MovieEntity;
import com.flixsync.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<MovieOutputDTO> findAll(Integer pageNumber, Integer amountPerPage){
        Pageable pageable = PageRequest.of(pageNumber, amountPerPage, Sort.by("id"));
        return movieRepository.findAll(pageable).map(MovieOutputDTO::new);
    }

    public MovieOutputDTO findById(Integer movieId) throws EntityNotFoundException{
        return new MovieOutputDTO(getMovieById(movieId));
    }

    public MovieOutputDTO save(MovieInputDTO input){
        MovieEntity movie = new MovieEntity(input);
        MovieEntity createdMovie = movieRepository.save(movie);
        return new MovieOutputDTO(createdMovie);
    }

    public MovieOutputDTO update(Integer movieId, Optional<String> name, Optional<Long> hours, Optional<Long> minutes,
                                 Optional<LocalDate> releaseDate, Optional<String> director, Optional<String> summary)
            throws EntityNotFoundException, InvalidParameterException {

        // Data validation phase
        if(name.isEmpty() && hours.isEmpty() && minutes.isEmpty() && releaseDate.isEmpty() && director.isEmpty() && summary.isEmpty()){
            throw new InvalidParameterException("At least one parameter has to be updated!");
        }

        if(name.isPresent() && name.get().length() > 255){
            throw new InvalidParameterException("Name has too many characters");
        }

        if(hours.isPresent() && minutes.isEmpty() || hours.isEmpty() && minutes.isPresent()){
            throw new InvalidParameterException("Both hours and minutes must be specified to update the movie's duration");
        }

        if(director.isPresent() && director.get().length() > 100){
            throw new InvalidParameterException("Director has too many characters");
        }

        // Updating data
        if(name.isPresent()) updateName(movieId, name.get());

        if(hours.isPresent()){ // If 'hours' is present, then 'minutes' is also present (validated on the previous phase)
            Duration duration = Duration.ofHours(hours.get()).plusMinutes(minutes.get());
            updateDuration(movieId, duration);
        }

        if(releaseDate.isPresent()) updateReleaseDate(movieId, releaseDate.get());

        if(director.isPresent()) updateDirector(movieId, director.get());

        if(summary.isPresent()) updateSummary(movieId, summary.get());

        MovieEntity updatedMovie = getMovieById(movieId);
        return new MovieOutputDTO(updatedMovie);
    }

    public void delete(Integer movieId) throws EntityNotFoundException{
        MovieEntity movie = getMovieById(movieId);
        movieRepository.delete(movie);
    }

    private MovieEntity getMovieById(Integer movieId) throws EntityNotFoundException{
        return movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("Movie not found"));
    }

    private void updateName(Integer movieId, String name) throws EntityNotFoundException{
        MovieEntity movie = getMovieById(movieId);
        movie.setName(name);
        movieRepository.save(movie);
    }

    private void updateDuration(Integer movieId, Duration duration) throws EntityNotFoundException{
        MovieEntity movie = getMovieById(movieId);
        movie.setDuration(duration);
        movieRepository.save(movie);
    }

    private void updateReleaseDate(Integer movieId, LocalDate releaseDate) throws EntityNotFoundException{
        MovieEntity movie = getMovieById(movieId);
        movie.setReleaseDate(releaseDate);
        movieRepository.save(movie);
    }

    private void updateDirector(Integer movieId, String director) throws EntityNotFoundException{
        MovieEntity movie = getMovieById(movieId);
        movie.setDirector(director);
        movieRepository.save(movie);
    }

    private void updateSummary(Integer movieId, String summary) throws EntityNotFoundException{
        MovieEntity movie = getMovieById(movieId);
        movie.setSummary(summary);
        movieRepository.save(movie);
    }

}
