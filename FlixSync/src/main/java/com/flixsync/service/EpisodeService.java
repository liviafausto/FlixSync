package com.flixsync.service;

import com.flixsync.exceptions.DuplicatedKeyException;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.episode.EpisodeInputDTO;
import com.flixsync.model.dto.episode.EpisodeOutputDTO;
import com.flixsync.model.entity.EpisodeEntity;
import com.flixsync.model.entity.EpisodePK;
import com.flixsync.model.entity.TvShowEntity;
import com.flixsync.repository.EpisodeRepository;
import com.flixsync.utils.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

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

    public EpisodeOutputDTO save(Integer tvShowId, EpisodeInputDTO episodeInput) throws EntityNotFoundException, InvalidParameterException, DuplicatedKeyException {
        ServiceLog serviceLog = new ServiceLog("EPISODE-SAVE", "episode");
        serviceLog.start("Register an episode");

        if(!StringUtils.valid(episodeInput.getDirector())){
            serviceLog.error("The episode's director was not provided");
            serviceLog.end();
            throw new InvalidParameterException("director: can't be blank");
        }

        TvShowEntity tvShow = tvShowService.getTvShowById(tvShowId, serviceLog);
        EpisodeEntity episode = new EpisodeEntity(tvShow, episodeInput);

        checkForDuplicatedKey(episode.getEpisodeId(), serviceLog);
        serviceLog.saveRequest(episodeInput.toString());

        EpisodeEntity createdEpisode = episodeRepository.save(episode);
        EpisodeOutputDTO createdEpisodeOutput = new EpisodeOutputDTO(createdEpisode);

        serviceLog.saveResponse(createdEpisodeOutput.toString());
        serviceLog.end();
        return createdEpisodeOutput;
    }

    public EpisodeOutputDTO update(Integer tvShowId, EpisodeInputDTO episodeInput) throws EntityNotFoundException, InvalidParameterException {
        ServiceLog serviceLog = new ServiceLog("EPISODE-UPDATE", "episode");
        serviceLog.start("Update an episode by ID");

        if(noParametersProvided(episodeInput)){
            serviceLog.error("No parameters were provided");
            serviceLog.end();
            throw new InvalidParameterException("At least one parameter has to be provided");
        }

        // Obtain the episode that will be updated
        TvShowEntity tvShow = tvShowService.getTvShowById(tvShowId, serviceLog);
        EpisodePK episodeId = new EpisodePK(tvShow, episodeInput.getSeason(), episodeInput.getNumber());
        EpisodeEntity episode = getEpisodeById(episodeId, serviceLog);

        // Validate which parameters will be updated and which will stay the same
        final List<String> attributes = new ArrayList<>(Arrays.asList("name", "duration", "release date", "director", "summary"));
        final HashMap<String, Object> currentParams = getHashMapFromEntity(episode);
        HashMap<String, Object> newParams = getHashMapFromInput(episodeInput);
        newParams = UpdateUtils.adjustParameters(attributes, currentParams, newParams);

        if(newParams == null){
            serviceLog.error("No new data was provided, so nothing will be updated");
            serviceLog.end();
            throw new InvalidParameterException("No new data was provided");
        }

        // Update data
        adjustEntityForUpdate(episode, newParams);
        serviceLog.updateRequest(episodeId.toString(), episode.toString());

        EpisodeEntity updatedEpisode = episodeRepository.save(episode);
        EpisodeOutputDTO updatedEpisodeOutput = new EpisodeOutputDTO(updatedEpisode);

        serviceLog.updateResponse(updatedEpisode.toString());
        serviceLog.end();
        return updatedEpisodeOutput;
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

    protected void checkForDuplicatedKey(EpisodePK episodeId, ServiceLog serviceLog) throws DuplicatedKeyException {
        serviceLog.setElementName("episode");
        serviceLog.searchRequest(episodeId.toString());

        Optional<EpisodeEntity> duplicatedEpisode = episodeRepository.findById(episodeId);

        if(duplicatedEpisode.isEmpty()){
            serviceLog.info("Episode not found");
        } else {
            serviceLog.searchResponse(duplicatedEpisode.get().toString());
            serviceLog.error("Duplicated key: the episode's ID already exists in the database");
            serviceLog.end();
            throw new DuplicatedKeyException("Episode '" + episodeId.formatted() + "' already exists");
        }
    }

    private boolean noParametersProvided(EpisodeInputDTO input){
        return input.getName() == null
                && input.getMinutes() == null
                && input.getReleaseDate() == null
                && input.getDirector() == null
                && input.getSummary() == null;
    }

    private HashMap<String, Object> getHashMapFromEntity(EpisodeEntity episode){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", episode.getName());
        map.put("duration", episode.getDuration());
        map.put("release date", episode.getReleaseDate());
        map.put("director", episode.getDirector());
        map.put("summary", episode.getSummary());
        return map;
    }

    private HashMap<String, Object> getHashMapFromInput(EpisodeInputDTO episodeInput){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", episodeInput.getName());
        map.put("duration", episodeInput.getMinutes());
        map.put("release date", episodeInput.getReleaseDate());
        map.put("director", episodeInput.getDirector());
        map.put("summary", episodeInput.getSummary());
        return map;
    }

    private EpisodeEntity getEpisodeWithUpdatedParams(EpisodePK episodeId, HashMap<String, Object> updatedParams){
        EpisodeEntity episode = new EpisodeEntity();
        episode.setEpisodeId(episodeId);

        episode.setName((String) updatedParams.get("name"));
        episode.setDuration((Duration) updatedParams.get("duration"));
        episode.setReleaseDate((LocalDate) updatedParams.get("release date"));
        episode.setDirector((String) updatedParams.get("director"));
        episode.setSummary((String) updatedParams.get("summary"));

        return episode;
    }

    private void adjustEntityForUpdate(EpisodeEntity episode, HashMap<String, Object> newParams){
        episode.setName((String) newParams.get("name"));
        episode.setDuration((Duration) newParams.get("duration"));
        episode.setReleaseDate((LocalDate) newParams.get("release date"));
        episode.setDirector((String) newParams.get("director"));
        episode.setSummary((String) newParams.get("summary"));
    }

}
