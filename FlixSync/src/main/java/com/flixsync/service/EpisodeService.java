package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.model.dto.episode.EpisodeOutputDTO;
import com.flixsync.model.entity.EpisodeEntity;
import com.flixsync.model.entity.TvShowEntity;
import com.flixsync.repository.EpisodeRepository;
import com.flixsync.utils.PageUtils;
import com.flixsync.utils.ServiceLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final TvShowService tvShowService;

    public Page<EpisodeOutputDTO> findAll(Integer tvShowId, Integer pageNumber, Integer pageSize) throws EntityNotFoundException {
        ServiceLog serviceLog = new ServiceLog("TV-SHOW-FIND-ALL-EPISODES", "episode");
        serviceLog.start("Find all TV show's episodes");

        TvShowEntity tvShow = tvShowService.getTvShowById(tvShowId, serviceLog);
        List<EpisodeEntity> episodesList = tvShow.getEpisodes().stream().toList();

        serviceLog.pageRequest(pageNumber, pageSize);

        final Sort pageSort = Sort.by("episodeId.season", "episodeId.number");
        Page<EpisodeEntity> episodesPage = PageUtils.getPage(episodesList, pageNumber, pageSize, pageSort);
        Page<EpisodeOutputDTO> episodesOutputPage = episodesPage.map(EpisodeOutputDTO::new);

        serviceLog.pageResponse(episodesPage.getNumberOfElements());
        serviceLog.end();
        return episodesOutputPage;
    }

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
