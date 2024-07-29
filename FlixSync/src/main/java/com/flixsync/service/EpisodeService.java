package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.model.dto.episode.EpisodeOutputDTO;
import com.flixsync.model.entity.EpisodeEntity;
import com.flixsync.model.entity.EpisodePK;
import com.flixsync.model.entity.TvShowEntity;
import com.flixsync.repository.EpisodeRepository;
import com.flixsync.utils.ListUtils;
import com.flixsync.utils.PageUtils;
import com.flixsync.utils.ServiceLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final TvShowService tvShowService;

    public Page<EpisodeOutputDTO> findAll(Integer tvShowId, Integer pageNumber, Integer pageSize) throws EntityNotFoundException {
        ServiceLog serviceLog = new ServiceLog("EPISODE-FIND-ALL", "episode");
        serviceLog.start("Find all TV show's episodes");

        TvShowEntity tvShow = tvShowService.getTvShowById(tvShowId, serviceLog);
        List<EpisodeEntity> episodesList = tvShow.getEpisodes().stream().toList();

        serviceLog.pageRequest(pageNumber, pageSize);

        final Sort pageSort = Sort.by("episodeId");
        Page<EpisodeEntity> episodesPage = PageUtils.getPage(episodesList, pageNumber, pageSize, pageSort);
        Page<EpisodeOutputDTO> episodesOutputPage = episodesPage.map(EpisodeOutputDTO::new);

        serviceLog.pageResponse(episodesPage.getNumberOfElements());
        serviceLog.end();
        return episodesOutputPage;
    }

    public List<EpisodeOutputDTO> findAllPerSeason(Integer tvShowId, Integer season) throws EntityNotFoundException {
        ServiceLog serviceLog = new ServiceLog("EPISODE-FIND-ALL-PER-SEASON", "episode");
        serviceLog.start("Find all TV show's episodes per season");

        TvShowEntity tvShow = tvShowService.getTvShowById(tvShowId, serviceLog);

        serviceLog.info("Requesting episodes from TV show '" + tvShow.getTitle() + "', season " + season);

        List<EpisodeEntity> episodesList = tvShow.getEpisodes().stream().filter(e -> e.getEpisodeId().getSeason().equals(season)).toList();
        List<EpisodeEntity> sortedEpisodes = ListUtils.sortList(episodesList, "episodeId");
        List<EpisodeOutputDTO> episodesOutput = sortedEpisodes.stream().map(EpisodeOutputDTO::new).toList();

        serviceLog.info(episodesList.size() + " episodes were retrieved");
        serviceLog.end();
        return episodesOutput;
    }

    public EpisodeOutputDTO findById(Integer tvShowId, Integer season, Integer episodeNumber) throws EntityNotFoundException {
        ServiceLog serviceLog = new ServiceLog("EPISODE-FIND-BY-ID", "episode");
        serviceLog.start("Find an episode by id");

        TvShowEntity tvShow = tvShowService.getTvShowById(tvShowId, serviceLog);
        EpisodePK episodeId = new EpisodePK(tvShow, season, episodeNumber);
        EpisodeEntity episode = getEpisodeById(episodeId, serviceLog);
        EpisodeOutputDTO episodeOutput = new EpisodeOutputDTO(episode);

        serviceLog.end();
        return episodeOutput;
    }

    protected EpisodeEntity getEpisodeById(EpisodePK episodeId, ServiceLog serviceLog) throws EntityNotFoundException {
        serviceLog.setElementName("episode");
        serviceLog.searchRequest(episodeId.toString());
        Optional<EpisodeEntity> optionalEpisode = episodeRepository.findById(episodeId);

        if(optionalEpisode.isEmpty()){
            serviceLog.error("Episode not found");
            serviceLog.end();
            throw new EntityNotFoundException("Episode not found");
        }

        EpisodeEntity episode = optionalEpisode.get();
        serviceLog.searchResponse(episode.toString());
        return episode;
    }
}
