package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.model.dto.tvshow.TvShowOutputDTO;
import com.flixsync.model.entity.TvShowEntity;
import com.flixsync.repository.TvShowRepository;
import com.flixsync.utils.ServiceLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        serviceLog.start("Find by id");

        TvShowEntity tvShow = getTvShowById(id, serviceLog);
        TvShowOutputDTO output = new TvShowOutputDTO(tvShow);

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
}
