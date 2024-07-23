package com.flixsync.controller;

import com.flixsync.documentation.MovieControllerDoc;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.movie.MovieInputDTO;
import com.flixsync.model.dto.movie.MovieOutputDTO;
import com.flixsync.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController implements MovieControllerDoc {
    private final MovieService movieService;

    @Override
    public ResponseEntity<Page<MovieOutputDTO>> findAll(Integer page, Integer size) {
        return new ResponseEntity<>(movieService.findAll(page, size), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MovieOutputDTO> findById(Integer id) throws EntityNotFoundException {
        return new ResponseEntity<>(movieService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MovieOutputDTO> save(MovieInputDTO movieInput) throws InvalidParameterException {
        return new ResponseEntity<>(movieService.save(movieInput), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MovieOutputDTO> update(Integer id, MovieInputDTO movieInput)
            throws EntityNotFoundException, InvalidParameterException {
        return new ResponseEntity<>(movieService.update(id, movieInput), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) throws EntityNotFoundException {
        movieService.delete(id);
        return ResponseEntity.ok().build();
    }
}
