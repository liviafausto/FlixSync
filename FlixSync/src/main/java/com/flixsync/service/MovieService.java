package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
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

import java.time.LocalDate;

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

    public MovieOutputDTO updateName(Integer movieId, String name) throws EntityNotFoundException{
        MovieEntity movie = getMovieById(movieId);
        movie.setName(name);
        MovieEntity updatedMovie = movieRepository.save(movie);
        return new MovieOutputDTO(updatedMovie);
    }

    public MovieOutputDTO updateDuration(Integer movieId, String duration) throws EntityNotFoundException{
        MovieEntity movie = getMovieById(movieId);
        movie.setDuration(duration);
        MovieEntity updatedMovie = movieRepository.save(movie);
        return new MovieOutputDTO(updatedMovie);
    }

    public MovieOutputDTO updateReleaseDate(Integer movieId, LocalDate releaseDate) throws EntityNotFoundException{
        MovieEntity movie = getMovieById(movieId);
        movie.setReleaseDate(releaseDate);
        MovieEntity updatedMovie = movieRepository.save(movie);
        return new MovieOutputDTO(updatedMovie);
    }

    public MovieOutputDTO updateDirector(Integer movieId, String director) throws EntityNotFoundException{
        MovieEntity movie = getMovieById(movieId);
        movie.setDirector(director);
        MovieEntity updatedMovie = movieRepository.save(movie);
        return new MovieOutputDTO(updatedMovie);
    }

    public MovieOutputDTO updateSummary(Integer movieId, String summary) throws EntityNotFoundException{
        MovieEntity movie = getMovieById(movieId);
        movie.setSummary(summary);
        MovieEntity updatedMovie = movieRepository.save(movie);
        return new MovieOutputDTO(updatedMovie);
    }

    public void delete(Integer movieId) throws EntityNotFoundException{
        MovieEntity movie = getMovieById(movieId);
        movieRepository.delete(movie);
    }

    private MovieEntity getMovieById(Integer movieId) throws EntityNotFoundException{
        return movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("Movie not found"));
    }

}
