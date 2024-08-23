package com.flixsync.controller;

import com.flixsync.documentation.EpisodeControllerDoc;
import com.flixsync.exceptions.DuplicatedKeyException;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.episode.EpisodeInputDTO;
import com.flixsync.model.dto.episode.EpisodeOutputDTO;
import com.flixsync.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/tv-shows/{id}/episodes")
@RequiredArgsConstructor
public class EpisodeController implements EpisodeControllerDoc {
    private final EpisodeService episodeService;

    @Override public ResponseEntity<Page<EpisodeOutputDTO>> findAll(Integer tvShowId, Integer page, Integer size) throws EntityNotFoundException {
        return new ResponseEntity<>(episodeService.findAll(tvShowId, page, size), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<EpisodeOutputDTO>> findAllPerSeason(Integer tvShowId, Integer season) throws EntityNotFoundException {
        return new ResponseEntity<>(episodeService.findAllPerSeason(tvShowId, season), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EpisodeOutputDTO> findById(Integer tvShowId, Integer season, Integer episodeNumber) throws EntityNotFoundException {
        return new ResponseEntity<>(episodeService.findById(tvShowId, season, episodeNumber), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EpisodeOutputDTO> save(Integer tvShowId, EpisodeInputDTO episodeInput) throws EntityNotFoundException, InvalidParameterException, DuplicatedKeyException {
        return new ResponseEntity<>(episodeService.save(tvShowId, episodeInput), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EpisodeOutputDTO> update(Integer tvShowId, EpisodeInputDTO episodeInput) throws EntityNotFoundException, InvalidParameterException {
        return new ResponseEntity<>(episodeService.update(tvShowId, episodeInput), HttpStatus.OK);
    }
}
