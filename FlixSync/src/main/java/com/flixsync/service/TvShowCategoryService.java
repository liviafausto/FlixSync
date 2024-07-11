package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.category.CategoryTvShowsListDTO;
import com.flixsync.model.entity.CategoryEntity;
import com.flixsync.model.entity.TvShowEntity;
import com.flixsync.utils.ServiceLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TvShowCategoryService {
    private final CategoryService categoryService;
    private final TvShowService tvShowService;

    public CategoryTvShowsListDTO findTvShowsByCategory(Integer categoryId) throws EntityNotFoundException{
        ServiceLog serviceLog = new ServiceLog("CATEGORY-FIND-TV-SHOWS-BY-ID", "category");
        serviceLog.start("Find TV shows by category's id");

        CategoryEntity category = categoryService.getCategoryById(categoryId, serviceLog);
        CategoryTvShowsListDTO tvShowsList = new CategoryTvShowsListDTO(category);

        if(tvShowsList.getTvShows().isEmpty()) {
            serviceLog.error("No TV shows found for category '" + category.getName() + "'");
        } else {
            serviceLog.info("Category's TV shows found --> " + tvShowsList);
        }

        serviceLog.end();
        return tvShowsList;
    }

    public CategoryTvShowsListDTO addTvShowToCategory(Integer categoryId, Integer tvShowId) throws EntityNotFoundException, InvalidParameterException {
        ServiceLog serviceLog = new ServiceLog("CATEGORY-ADD-TV-SHOW", "category");
        serviceLog.start("Add TV show to category");

        CategoryEntity category = categoryService.getCategoryById(categoryId, serviceLog);
        TvShowEntity tvShow = tvShowService.getTvShowById(tvShowId, serviceLog);

        if(tvShow.getTvShowCategories().contains(category)){
            final String errorMessage = "TV show '" + tvShow.getTitle() + "' is already part of category '" + category.getName() + "'";
            serviceLog.error(errorMessage);
            serviceLog.end();
            throw new InvalidParameterException(errorMessage);
        }

        TvShowEntity updatedTvShow = tvShowService.addCategory(tvShow, category, serviceLog);
        CategoryTvShowsListDTO updatedTvShowsList = categoryService.addTvShow(category, updatedTvShow, serviceLog);

        serviceLog.end();
        return updatedTvShowsList;
    }

    public CategoryTvShowsListDTO removeTvShowFromCategory(Integer categoryId, Integer tvShowId) throws EntityNotFoundException, InvalidParameterException {
        ServiceLog serviceLog = new ServiceLog("CATEGORY-REMOVE-TV-SHOW", "category");
        serviceLog.start("Remove TV show from category");

        CategoryEntity category = categoryService.getCategoryById(categoryId, serviceLog);
        TvShowEntity tvShow = tvShowService.getTvShowById(tvShowId, serviceLog);

        if(!tvShow.getTvShowCategories().contains(category)){
            final String errorMessage = "TV show '" + tvShow.getTitle() + "' is not part of category '" + category.getName() + "'";
            serviceLog.error(errorMessage);
            serviceLog.end();
            throw new InvalidParameterException(errorMessage);
        }

        TvShowEntity updatedTvShow = tvShowService.removeCategory(tvShow, category, serviceLog);
        CategoryTvShowsListDTO updatedTvShowsList = categoryService.removeTvShow(category, updatedTvShow, serviceLog);

        serviceLog.end();
        return updatedTvShowsList;
    }
}
