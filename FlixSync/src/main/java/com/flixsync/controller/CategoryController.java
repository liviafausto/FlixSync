package com.flixsync.controller;

import com.flixsync.documentation.CategoryControllerDoc;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.category.CategoryMoviesListDTO;
import com.flixsync.model.dto.category.CategoryOutputDTO;
import com.flixsync.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController implements CategoryControllerDoc {
    private final CategoryService categoryService;

    @Override
    public ResponseEntity<Page<CategoryOutputDTO>> findAll(Integer page, Integer amount) {
        return new ResponseEntity<>(categoryService.findAll(page, amount), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryOutputDTO> findById(Integer id) throws EntityNotFoundException {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryMoviesListDTO> findMoviesById(Integer categoryId) throws EntityNotFoundException {
        return new ResponseEntity<>(categoryService.findMoviesById(categoryId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryOutputDTO> save(String name) {
        return new ResponseEntity<>(categoryService.save(name), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryOutputDTO> update(Integer id, String name) throws EntityNotFoundException, InvalidParameterException {
        return new ResponseEntity<>(categoryService.update(id, name), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) throws EntityNotFoundException {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CategoryMoviesListDTO> addMovie(Integer categoryId, Integer movieId) throws EntityNotFoundException, InvalidParameterException {
        return new ResponseEntity<>(categoryService.addMovie(categoryId, movieId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryMoviesListDTO> removeMovie(Integer categoryId, Integer movieId) throws EntityNotFoundException, InvalidParameterException {
        return new ResponseEntity<>(categoryService.removeMovie(categoryId, movieId), HttpStatus.OK);
    }
}
