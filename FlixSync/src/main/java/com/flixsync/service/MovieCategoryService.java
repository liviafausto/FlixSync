package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.category.CategoryMoviesListDTO;
import com.flixsync.model.entity.CategoryEntity;
import com.flixsync.model.entity.MovieEntity;
import com.flixsync.utils.ServiceLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieCategoryService {
    private final CategoryService categoryService;
    private final MovieService movieService;

    public CategoryMoviesListDTO findMoviesByCategory(Integer categoryId) throws EntityNotFoundException {
        ServiceLog serviceLog = new ServiceLog("CATEGORY-FIND-MOVIES-BY-ID", "category");
        serviceLog.start("Find movies by category's id");

        CategoryEntity category = categoryService.getCategoryById(categoryId, serviceLog);
        CategoryMoviesListDTO moviesList = new CategoryMoviesListDTO(category);

        if(moviesList.getMovies().isEmpty()) {
            serviceLog.error("No movies found for category '" + category.getName() + "'");
        } else {
            serviceLog.info("Category's movies found --> " + moviesList);
        }

        serviceLog.end();
        return moviesList;
    }

    public CategoryMoviesListDTO addMovie(Integer categoryId, Integer movieId) throws EntityNotFoundException, InvalidParameterException {
        ServiceLog serviceLog = new ServiceLog("CATEGORY-ADD-MOVIE", "category");
        serviceLog.start("Add movie to category");

        CategoryEntity category = categoryService.getCategoryById(categoryId, serviceLog);
        MovieEntity movie = movieService.getMovieById(movieId, serviceLog);

        if(movie.getCategories().contains(category)){
            final String errorMessage = "Movie '" + movie.getName() + "' is already part of category '" + category.getName() + "'";
            serviceLog.error(errorMessage);
            serviceLog.end();
            throw new InvalidParameterException(errorMessage);
        }

        MovieEntity updatedMovie = movieService.addCategory(movie, category, serviceLog);
        CategoryMoviesListDTO updatedMoviesList = categoryService.addMovie(category, updatedMovie, serviceLog);

        serviceLog.end();
        return updatedMoviesList;
    }

    public CategoryMoviesListDTO removeMovie(Integer categoryId, Integer movieId) throws EntityNotFoundException, InvalidParameterException {
        ServiceLog serviceLog = new ServiceLog("CATEGORY-REMOVE-MOVIE", "category");
        serviceLog.start("Remove movie from category");

        CategoryEntity category = categoryService.getCategoryById(categoryId, serviceLog);
        MovieEntity movie = movieService.getMovieById(movieId, serviceLog);

        if(!movie.getCategories().contains(category)){
            final String errorMessage = "Movie '" + movie.getName() + "' is not part of category '" + category.getName() + "'";
            serviceLog.error(errorMessage);
            serviceLog.end();
            throw new InvalidParameterException(errorMessage);
        }

        MovieEntity updatedMovie = movieService.removeCategory(movie, category, serviceLog);
        CategoryMoviesListDTO updatedMoviesList = categoryService.removeMovie(category, updatedMovie, serviceLog);

        serviceLog.end();
        return updatedMoviesList;
    }
}
