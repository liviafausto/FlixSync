package com.flixsync.controller;

import com.flixsync.documentation.EpisodeControllerDoc;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.model.dto.episode.EpisodeOutputDTO;
import com.flixsync.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/tv-shows/{id}/episodes")
@RequiredArgsConstructor
public class EpisodeController implements EpisodeControllerDoc {
    private final EpisodeService episodeService;

    @Override
    public ResponseEntity<List<EpisodeOutputDTO>> findAllPerSeason(Integer tvShowId, Integer season) throws EntityNotFoundException {
        return new ResponseEntity<>(episodeService.findAllPerSeason(tvShowId, season), HttpStatus.OK);
    }
}