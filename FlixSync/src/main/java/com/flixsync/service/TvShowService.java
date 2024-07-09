package com.flixsync.service;

import com.flixsync.model.dto.tvshow.TvShowOutputDTO;
import com.flixsync.model.entity.TvShowEntity;
import com.flixsync.repository.TvShowRepository;
import com.flixsync.utils.ServiceLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        Page<TvShowOutputDTO> tvShowsOutput = tvShows.map(TvShowOutputDTO::new);

        serviceLog.pageResponse(tvShowsOutput.getNumberOfElements());
        serviceLog.end();
        return tvShowsOutput;
    }
}
