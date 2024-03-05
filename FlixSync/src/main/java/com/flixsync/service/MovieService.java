package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.movie.MovieInputDTO;
import com.flixsync.model.dto.movie.MovieOutputDTO;
import com.flixsync.model.dto.movie.MovieUpdateInputDTO;
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
import java.time.LocalDateTime;
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

    public MovieOutputDTO update(Integer movieId, MovieUpdateInputDTO movieUpdateInput)
            throws EntityNotFoundException, InvalidParameterException {
        final String NAME = movieUpdateInput.getName();
        final Long HOURS = movieUpdateInput.getHours();
        final Long MINUTES = movieUpdateInput.getMinutes();
        final LocalDate RELEASE_DATE = movieUpdateInput.getReleaseDate();
        final String DIRECTOR = movieUpdateInput.getDirector();
        final String SUMMARY = movieUpdateInput.getSummary();

        // Data validation phase
        if(NAME == null && HOURS == null && MINUTES == null && RELEASE_DATE == null && DIRECTOR == null && SUMMARY == null){
            throw new InvalidParameterException("At least one parameter has to be updated!");
        }

        if(HOURS != null && MINUTES == null || HOURS == null && MINUTES != null){
            throw new InvalidParameterException("Both hours and minutes must be specified to update the movie's duration");
        }

        // Updating data
        if(NAME != null) updateName(movieId, NAME);

        if(HOURS != null){ // If 'hours' is present, then 'minutes' is also present (validated on the previous phase)
            Duration duration = Duration.ofHours(HOURS).plusMinutes(MINUTES);
            updateDuration(movieId, duration);
        }

        if(RELEASE_DATE != null) updateReleaseDate(movieId, RELEASE_DATE);

        if(DIRECTOR != null) updateDirector(movieId, DIRECTOR);

        if(SUMMARY != null) updateSummary(movieId, SUMMARY);

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
