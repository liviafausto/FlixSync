package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.model.dto.episode.EpisodeOutputDTO;
import com.flixsync.model.entity.EpisodeEntity;
import com.flixsync.model.entity.TvShowEntity;
import com.flixsync.repository.EpisodeRepository;
import com.flixsync.utils.ServiceLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final TvShowService tvShowService;

    public List<EpisodeOutputDTO> findAllPerSeason(Integer tvShowId, Integer season) throws EntityNotFoundException {
        ServiceLog serviceLog = new ServiceLog("TV-SHOW-FIND-EPISODES-PER-SEASON", "episode");
        serviceLog.start("Find all TV show's episodes per season");

        TvShowEntity tvShow = tvShowService.getTvShowById(tvShowId, serviceLog);

        serviceLog.info("Requesting episodes from TV show '" + tvShow.getTitle() + "', season " + season);

        List<EpisodeEntity> episodesList = tvShow.getEpisodes().stream().filter(e -> e.getEpisodeId().getSeason().equals(season)).toList();
        List<EpisodeOutputDTO> episodesOutput = episodesList.stream().map(EpisodeOutputDTO::new).toList();

        serviceLog.info(episodesList.size() + " episodes were retrieved");
        serviceLog.end();
        return episodesOutput;
    }
}
