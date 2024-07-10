package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.tvshow.TvShowInputDTO;
import com.flixsync.model.dto.tvshow.TvShowOutputDTO;
import com.flixsync.model.entity.TvShowEntity;
import com.flixsync.repository.TvShowRepository;
import com.flixsync.utils.MovieDuration;
import com.flixsync.utils.ServiceLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TvShowService {
    private final TvShowRepository tvShowRepository;

    public Page<TvShowOutputDTO> findAll(Integer pageNumber, Integer amountPerPage){
        ServiceLog serviceLog = new ServiceLog("TV-SHOW-FIND-ALL", "TV show");
        serviceLog.start("Find all TV shows");
        serviceLog.pageRequest(pageNumber, amountPerPage);

        PageRequest pageRequest = PageRequest.of(pageNumber, amountPerPage, Sort.by("id"));
        Page<TvShowEntity> tvShows = tvShowRepository.findAll(pageRequest);
        Page<TvShowOutputDTO> output = tvShows.map(TvShowOutputDTO::new);

        serviceLog.pageResponse(tvShows.getNumberOfElements());
        serviceLog.end();
        return output;
    }

    public TvShowOutputDTO findById(Integer id) throws EntityNotFoundException {
        ServiceLog serviceLog = new ServiceLog("TV-SHOW-FIND-BY-ID", "TV show");
        serviceLog.start("Find a TV show by id");

        TvShowEntity tvShow = getTvShowById(id, serviceLog);
        TvShowOutputDTO output = new TvShowOutputDTO(tvShow);

        serviceLog.end();
        return output;
    }

    public TvShowOutputDTO save(TvShowInputDTO tvShowInput) throws InvalidParameterException {
        ServiceLog serviceLog = new ServiceLog("TV-SHOW-SAVE", "TV show");
        serviceLog.start("Register a TV show");
        serviceLog.saveRequest(tvShowInput.toString());

        if(!validString(tvShowInput.getTitle())){
            serviceLog.error("The TV show's title was not provided");
            serviceLog.end();
            throw new InvalidParameterException("title: can't be blank");
        }

        TvShowEntity tvShow = new TvShowEntity(tvShowInput);
        TvShowEntity createdTvShow = tvShowRepository.save(tvShow);
        TvShowOutputDTO output = new TvShowOutputDTO(createdTvShow);

        serviceLog.saveResponse(createdTvShow.toString());
        serviceLog.end();
        return output;
    }

    public TvShowOutputDTO update(Integer tvShowId, TvShowInputDTO tvShowInput) throws InvalidParameterException, EntityNotFoundException {
        ServiceLog serviceLog = new ServiceLog("TV-SHOW-UPDATE", "TV show");
        serviceLog.start("Update a TV show by id");

        String NEW_TITLE = tvShowInput.getTitle();
        Duration NEW_AVERAGE_DURATION = MovieDuration.getDuration(null, tvShowInput.getMinutesPerEpisode());
        String NEW_SUMMARY = tvShowInput.getSummary();

        // Data validation phase
        if(NEW_TITLE == null && NEW_AVERAGE_DURATION == null && NEW_SUMMARY == null){
            serviceLog.error("No parameters were provided");
            serviceLog.end();
            throw new InvalidParameterException("At least one parameter has to be provided");
        }

        TvShowEntity tvShow = getTvShowById(tvShowId, serviceLog);
        boolean newDataProvided = false;

        if(validString(NEW_TITLE) && !NEW_TITLE.equals(tvShow.getTitle())){
            serviceLog.updateRequest("title", tvShowId, tvShow.getTitle(), NEW_TITLE);
            tvShow.setTitle(NEW_TITLE);
            newDataProvided = true;
        }
        if(NEW_AVERAGE_DURATION != null && !NEW_AVERAGE_DURATION.equals(tvShow.getAverageDuration())){
            serviceLog.updateRequest("averageDuration", tvShowId, MovieDuration.format(tvShow.getAverageDuration()), MovieDuration.format(NEW_AVERAGE_DURATION));
            tvShow.setAverageDuration(NEW_AVERAGE_DURATION);
            newDataProvided = true;
        }
        if(validString(NEW_SUMMARY) && !NEW_SUMMARY.equals(tvShow.getSummary())){
            serviceLog.updateRequest("summary", tvShowId, tvShow.getSummary(), NEW_SUMMARY);
            tvShow.setSummary(NEW_SUMMARY);
            newDataProvided = true;
        }

        if(!newDataProvided){
            serviceLog.error("No new data was provided, so nothing will be updated");
            serviceLog.end();
            throw new InvalidParameterException("The TV show already has the provided data");
        }

        // Updating data
        TvShowEntity updatedTvShow = tvShowRepository.save(tvShow);
        TvShowOutputDTO output = new TvShowOutputDTO(updatedTvShow);

        serviceLog.updateResponse(updatedTvShow.toString());
        serviceLog.end();
        return output;
    }

    protected TvShowEntity getTvShowById(Integer tvShowId, ServiceLog serviceLog) throws EntityNotFoundException {
        serviceLog.setElementName("TV show");
        serviceLog.searchRequest(tvShowId);
        Optional<TvShowEntity> optionalTvShow = tvShowRepository.findById(tvShowId);

        if(optionalTvShow.isEmpty()){
            serviceLog.error("TV show not found");
            serviceLog.end();
            throw new EntityNotFoundException("TV show not found");
        }

        TvShowEntity tvShow = optionalTvShow.get();
        serviceLog.searchResponse(tvShow.toString());
        return tvShow;
    }

    private boolean validString(String string){
        return string != null && !string.isEmpty() && !string.isBlank();
    }
}
