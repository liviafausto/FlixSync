package com.flixsync.controller;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.model.dto.movie.MovieInputDTO;
import com.flixsync.model.dto.movie.MovieOutputDTO;
import com.flixsync.service.MovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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

    @GetMapping("/remove")
    public ResponseEntity<Void> delete(@RequestParam(name = "id") @Valid @Positive Integer id) throws EntityNotFoundException {
        movieService.delete(id);
        return ResponseEntity.ok().build();
    }
}
