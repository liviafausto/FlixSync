package com.flixsync.controller;

import com.flixsync.documentation.TvShowCategoryControllerDoc;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.category.CategoryTvShowsListDTO;
import com.flixsync.service.TvShowCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/categories/tv-shows-list")
@RequiredArgsConstructor
public class TvShowCategoryController implements TvShowCategoryControllerDoc {
    final TvShowCategoryService tvShowCategoryService;

    @Override
    public ResponseEntity<CategoryTvShowsListDTO> findTvShowsByCategory(Integer categoryId) throws EntityNotFoundException {
        return new ResponseEntity<>(tvShowCategoryService.findTvShowsByCategory(categoryId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryTvShowsListDTO> addTvShowToCategory(Integer categoryId, Integer tvShowId) throws EntityNotFoundException, InvalidParameterException {
        return new ResponseEntity<>(tvShowCategoryService.addTvShowToCategory(categoryId, tvShowId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryTvShowsListDTO> removeTvShowFromCategory(Integer categoryId, Integer tvShowId) throws EntityNotFoundException, InvalidParameterException {
        return new ResponseEntity<>(tvShowCategoryService.removeTvShowFromCategory(categoryId, tvShowId), HttpStatus.OK);
    }
}
