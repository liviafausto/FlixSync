package com.flixsync.controller;

import com.flixsync.documentation.CategoryControllerDoc;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.category.CategoryOutputDTO;
import com.flixsync.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController implements CategoryControllerDoc {
    private final CategoryService categoryService;

    @Override
    public ResponseEntity<Page<CategoryOutputDTO>> findAll(Integer page, Integer size) {
        return new ResponseEntity<>(categoryService.findAll(page, size), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryOutputDTO> findById(Integer id) throws EntityNotFoundException {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
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
}
