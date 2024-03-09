package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.movie.MovieInputDTO;
import com.flixsync.model.dto.movie.MovieOutputDTO;
import com.flixsync.model.dto.movie.MovieUpdateInputDTO;
import com.flixsync.model.entity.MovieEntity;
import com.flixsync.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public Page<MovieOutputDTO> findAll(Integer pageNumber, Integer amountPerPage){
        log.info("<< FIND-ALL >> Service started: Find all movies");
        log.info("<< FIND-ALL >> Retrieving page {} with {} movies", pageNumber, amountPerPage);
        Pageable pageable = PageRequest.of(pageNumber, amountPerPage, Sort.by("id"));
        Page<MovieEntity> movies = movieRepository.findAll(pageable);
        log.info("<< FIND-ALL >> {} movies were retrieved!", movies.getNumberOfElements());
        log.info("<< FIND-ALL >> End of service");
        return movies.map(MovieOutputDTO::new);
    }

    public MovieOutputDTO findById(Integer movieId) throws EntityNotFoundException{
        log.info("<< FIND-BY-ID >> Service started: Find a movie by id");
        MovieOutputDTO movieOutput = new MovieOutputDTO(getMovieById(movieId));
        log.info("<< FIND-BY-ID >> End of service");
        return movieOutput;
    }

    public MovieOutputDTO save(MovieInputDTO input){
        log.info("<< SAVE >> Service started: Register a movie");
        MovieEntity movie = new MovieEntity(input);
        log.info("<< SAVE >> Inserting a new movie in the database --> {name='{}', duration='{}', releaseDate='{}', director='{}', summary='{}'}",
                movie.getName(), MovieOutputDTO.getDurationOutput(movie), movie.getReleaseDate(), movie.getDirector(), movie.getSummary());
        MovieEntity createdMovie = movieRepository.save(movie);
        MovieOutputDTO movieOutput = new MovieOutputDTO(createdMovie);
        log.info("<< SAVE >> Movie inserted --> {id='{}', name='{}', duration='{}', releaseDate='{}', director='{}', summary='{}'}",
                movieOutput.getId(), movieOutput.getName(), movieOutput.getDuration(), movieOutput.getReleaseDate(), movieOutput.getDirector(), movieOutput.getSummary());
        log.info("<< SAVE >> End of service");
        return movieOutput;
    }

    public MovieOutputDTO update(Integer movieId, MovieUpdateInputDTO movieUpdateInput)
            throws EntityNotFoundException, InvalidParameterException {
        log.info("<< UPDATE >> Service started: Update a movie by id");
        final String NAME = movieUpdateInput.getName();
        final Long HOURS = movieUpdateInput.getHours();
        final Long MINUTES = movieUpdateInput.getMinutes();
        final LocalDate RELEASE_DATE = movieUpdateInput.getReleaseDate();
        final String DIRECTOR = movieUpdateInput.getDirector();
        final String SUMMARY = movieUpdateInput.getSummary();

        // Data validation phase
        if(NAME == null && HOURS == null && MINUTES == null && RELEASE_DATE == null && DIRECTOR == null && SUMMARY == null){
            log.error("<< UPDATE >> No parameters were provided");
            throw new InvalidParameterException("At least one parameter has to be provided!");
        }
        if(HOURS != null && MINUTES == null || HOURS == null && MINUTES != null){
            log.error("<< UPDATE >> Movie's duration wasn't correctly informed");
            throw new InvalidParameterException("Both hours and minutes must be specified to update the movie's duration");
        }

        // Updating data
        MovieEntity movie = getMovieById(movieId);
        if(NAME != null) movie = updateName(movie, NAME);
        if(HOURS != null){ // If 'hours' is present, then 'minutes' is also present (validated on the previous phase)
            Duration duration = Duration.ofHours(HOURS).plusMinutes(MINUTES);
            movie = updateDuration(movie, duration);
        }
        if(RELEASE_DATE != null) movie = updateReleaseDate(movie, RELEASE_DATE);
        if(DIRECTOR != null) movie = updateDirector(movie, DIRECTOR);
        if(SUMMARY != null) movie = updateSummary(movie, SUMMARY);

        MovieOutputDTO movieOutput = new MovieOutputDTO(movie);
        log.info("<< UPDATE >> Movie updated --> {id='{}', name='{}', duration='{}', releaseDate='{}', director='{}', summary='{}'}",
                movieOutput.getId(), movieOutput.getName(), movieOutput.getDuration(), movieOutput.getReleaseDate(), movieOutput.getDirector(), movieOutput.getSummary());
        log.info("<< UPDATE >> End of service");
        return movieOutput;
    }

    public void delete(Integer movieId) throws EntityNotFoundException{
        log.info("<< DELETE >> Service started: Remove a movie by id");
        MovieEntity movie = getMovieById(movieId);
        log.info("<< DELETE >> Removing movie from the database --> {id='{}', name='{}', director='{}'}",
                movie.getId(), movie.getName(), movie.getDirector());
        movieRepository.delete(movie);
        log.info("<< DELETE >> End of service");
    }

    private MovieEntity getMovieById(Integer movieId) throws EntityNotFoundException{
        log.info("<< FIND-BY-ID >> Searching for movie - ID: {}", movieId);
        Optional<MovieEntity> optionalMovie = movieRepository.findById(movieId);
        if(optionalMovie.isPresent()){
            MovieEntity movie = optionalMovie.get();
            final String DURATION = MovieOutputDTO.getDurationOutput(movie);
            log.info("<< FIND-BY-ID >> Movie found --> {id='{}', name='{}', duration='{}', releaseDate='{}', director='{}', summary='{}'}",
                    movie.getId(), movie.getName(), DURATION, movie.getReleaseDate(), movie.getDirector(), movie.getSummary());
            return movie;
        }
        log.error("<< FIND-BY-ID >> Movie not found");
        throw new EntityNotFoundException("Movie not found");
    }

    private MovieEntity updateName(MovieEntity movie, String name) throws EntityNotFoundException{
        log.info("<< UPDATE-NAME >> Updating movie's name --> {id='{}', current='{}', new='{}'}", movie.getId(), movie.getName(), name);
        movie.setName(name);
        return movieRepository.save(movie);
    }

    private MovieEntity updateDuration(MovieEntity movie, Duration duration) throws EntityNotFoundException{
        log.info("<< UPDATE-DURATION >> Updating movie's duration --> {id='{}', current='{}', new='{}'}", movie.getId(), movie.getDuration(), duration);
        movie.setDuration(duration);
        return movieRepository.save(movie);
    }

    private MovieEntity updateReleaseDate(MovieEntity movie, LocalDate releaseDate) throws EntityNotFoundException{
        log.info("<< UPDATE-RELEASE-DATE >> Updating movie's release date --> {id='{}', current='{}', new='{}'}", movie.getId(), movie.getReleaseDate(), releaseDate);
        movie.setReleaseDate(releaseDate);
        return movieRepository.save(movie);
    }

    private MovieEntity updateDirector(MovieEntity movie, String director) throws EntityNotFoundException{
        log.info("<< UPDATE-DIRECTOR >> Updating movie's director --> {id='{}', current='{}', new='{}'}", movie.getId(), movie.getDirector(), director);
        movie.setDirector(director);
        return movieRepository.save(movie);
    }

    private MovieEntity updateSummary(MovieEntity movie, String summary) throws EntityNotFoundException{
        log.info("<< UPDATE-SUMMARY >> Updating movie's summary --> {id='{}', current='{}', new='{}'}", movie.getId(), movie.getSummary(), summary);
        movie.setSummary(summary);
        return movieRepository.save(movie);
    }

}
