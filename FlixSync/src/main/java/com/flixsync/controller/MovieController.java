package com.flixsync.controller;

import com.flixsync.exceptions.DatabaseException;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.movie.MovieInputDTO;
import com.flixsync.model.dto.movie.MovieOutputDTO;
import com.flixsync.service.MovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<Page<MovieOutputDTO>> findAll(@RequestParam(name="page", defaultValue = "0") @Valid @PositiveOrZero Integer page,
                                                        @RequestParam(name="amount", defaultValue = "10") @Valid @Positive Integer amount){
        return new ResponseEntity<>(movieService.findAll(page, amount), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieOutputDTO> findById(@PathVariable(name = "id") @Valid @Positive Integer id) throws EntityNotFoundException {
        return new ResponseEntity<>(movieService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<MovieOutputDTO> save(@RequestBody @Valid MovieInputDTO movieInput){
        return new ResponseEntity<>(movieService.save(movieInput), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<MovieOutputDTO> update(@RequestParam(name = "id") @Valid @Positive Integer id,
                                                 @RequestParam(name = "name") Optional<String> name,
                                                 @RequestParam(name = "hours")  Optional<Long> hours,
                                                 @RequestParam(name = "minutes") Optional<Long> minutes,
                                                 @RequestParam(name = "release-date") Optional<LocalDate> releaseDate,
                                                 @RequestParam(name = "director") Optional<String> director,
                                                 @RequestParam(name = "summary") Optional<String> summary)
            throws EntityNotFoundException, InvalidParameterException {
        return new ResponseEntity<>(movieService.update(id, name, hours, minutes, releaseDate, director, summary), HttpStatus.OK);
    }

    @GetMapping("/remove")
    public ResponseEntity<Void> delete(@RequestParam(name = "id") @Valid @Positive Integer id) throws EntityNotFoundException {
        movieService.delete(id);
        return ResponseEntity.ok().build();
    }
}
