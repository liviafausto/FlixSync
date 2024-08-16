package com.flixsync.controller;

import com.flixsync.documentation.MovieCategoryControllerDoc;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.category.CategoryMoviesListDTO;
import com.flixsync.service.MovieCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/categories/{id}/movies-list")
@RequiredArgsConstructor
public class MovieCategoryController implements MovieCategoryControllerDoc {
    private final MovieCategoryService movieCategoryService;

    @Override
    public ResponseEntity<CategoryMoviesListDTO> findMoviesByCategory(Integer categoryId) throws EntityNotFoundException {
        return new ResponseEntity<>(movieCategoryService.findMoviesByCategory(categoryId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryMoviesListDTO> addMovie(Integer categoryId, Integer movieId) throws EntityNotFoundException, InvalidParameterException {
        return new ResponseEntity<>(movieCategoryService.addMovie(categoryId, movieId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryMoviesListDTO> removeMovie(Integer categoryId, Integer movieId) throws EntityNotFoundException, InvalidParameterException {
        return new ResponseEntity<>(movieCategoryService.removeMovie(categoryId, movieId), HttpStatus.OK);
    }
}
