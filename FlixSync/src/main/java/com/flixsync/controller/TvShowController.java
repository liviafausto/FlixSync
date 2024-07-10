package com.flixsync.controller;

import com.flixsync.documentation.TvShowControllerDoc;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.tvshow.TvShowInputDTO;
import com.flixsync.model.dto.tvshow.TvShowOutputDTO;
import com.flixsync.service.TvShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/tv-shows")
@RequiredArgsConstructor
public class TvShowController implements TvShowControllerDoc {
    private final TvShowService tvShowService;

    @Override
    public ResponseEntity<Page<TvShowOutputDTO>> findAll(Integer page, Integer amount) {
        return new ResponseEntity<>(tvShowService.findAll(page, amount), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TvShowOutputDTO> findById(Integer id) throws EntityNotFoundException {
        return new ResponseEntity<>(tvShowService.findById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TvShowOutputDTO> save(TvShowInputDTO tvShowInput) throws InvalidParameterException {
        return new ResponseEntity<>(tvShowService.save(tvShowInput), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TvShowOutputDTO> update(Integer id, TvShowInputDTO tvShowInput) throws EntityNotFoundException, InvalidParameterException {
        return new ResponseEntity<>(tvShowService.update(id, tvShowInput), HttpStatus.OK);
    }
}
