package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import com.flixsync.model.dto.category.CategoryMoviesListDTO;
import com.flixsync.model.dto.category.CategoryOutputDTO;
import com.flixsync.model.dto.category.CategoryTvShowsListDTO;
import com.flixsync.model.entity.CategoryEntity;
import com.flixsync.model.entity.MovieEntity;
import com.flixsync.model.entity.TvShowEntity;
import com.flixsync.repository.CategoryRepository;
import com.flixsync.utils.ServiceLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final MovieService movieService;

    public Page<CategoryOutputDTO> findAll(Integer pageNumber, Integer amountPerPage){
        ServiceLog serviceLog = new ServiceLog("CATEGORY-FIND-ALL", "category");
        serviceLog.start("Find all categories");
        serviceLog.pageRequest(pageNumber, amountPerPage);

        Page<CategoryEntity> categoriesPage = categoryRepository.findAll(PageRequest.of(pageNumber, amountPerPage, Sort.by("id")));
        Page<CategoryOutputDTO> categoriesPageOutput = categoriesPage.map(CategoryOutputDTO::new);

        serviceLog.pageResponse(categoriesPage.getNumberOfElements());
        serviceLog.end();
        return categoriesPageOutput;
    }

    public CategoryOutputDTO findById(Integer categoryId) throws EntityNotFoundException {
        ServiceLog serviceLog = new ServiceLog("CATEGORY-FIND-BY-ID", "category");
        serviceLog.start("Find a category by id");

        CategoryEntity category = getCategoryById(categoryId, serviceLog);
        CategoryOutputDTO categoryOutput = new CategoryOutputDTO(category);

        serviceLog.end();
        return categoryOutput;
    }

    public CategoryOutputDTO save(String name){
        ServiceLog serviceLog = new ServiceLog("CATEGORY-SAVE", "category");
        serviceLog.start("Register a category");
        serviceLog.saveRequest("{name: '" + name + "'}");

        CategoryEntity category = new CategoryEntity(name);
        CategoryEntity createdCategory = categoryRepository.save(category);
        CategoryOutputDTO createdCategoryOutput = new CategoryOutputDTO(createdCategory);

        serviceLog.saveResponse(createdCategory.toString());
        serviceLog.end();
        return createdCategoryOutput;
    }

    public CategoryOutputDTO update(Integer categoryId, String name) throws EntityNotFoundException, InvalidParameterException {
        ServiceLog serviceLog = new ServiceLog("CATEGORY-UPDATE", "category");
        serviceLog.start("Update a category by id");

        CategoryEntity category = getCategoryById(categoryId, serviceLog);

        if(name.equals(category.getName())){
            final String errorMessage = "Category " + categoryId + " is already named '" + name + "'";
            serviceLog.error(errorMessage);
            serviceLog.end();
            throw new InvalidParameterException(errorMessage);
        }

        serviceLog.updateRequest("name", category.getId(), category.getName(), name);
        category.setName(name);

        CategoryEntity updatedCategory = categoryRepository.save(category);
        CategoryOutputDTO updatedCategoryOutput = new CategoryOutputDTO(updatedCategory);

        serviceLog.updateResponse(updatedCategory.toString());
        serviceLog.end();
        return updatedCategoryOutput;
    }

    public void delete(Integer categoryId) throws EntityNotFoundException {
        ServiceLog serviceLog = new ServiceLog("CATEGORY-DELETE", "category");
        serviceLog.start("Remove a category by id");

        CategoryEntity category = getCategoryById(categoryId, serviceLog);
        serviceLog.deleteRequest(category.toString());
        categoryRepository.delete(category);
        serviceLog.deleteResponse(categoryId);

        serviceLog.end();
    }

    protected CategoryEntity getCategoryById(Integer categoryId, ServiceLog serviceLog) throws EntityNotFoundException {
        serviceLog.setElementName("category");
        serviceLog.searchRequest(categoryId);
        Optional<CategoryEntity> category = categoryRepository.findById(categoryId);

        if(category.isEmpty()){
            serviceLog.error("Category not found");
            serviceLog.end();
            throw new EntityNotFoundException("Category not found");
        }

        serviceLog.searchResponse(category.get().toString());
        return category.get();
    }

    protected CategoryMoviesListDTO addMovie(CategoryEntity category, MovieEntity movie, ServiceLog serviceLog){
        serviceLog.setElementName("category");
        serviceLog.info("Adding movie '" + movie.getName() + "' to category '" + category.getName() + "'");

        category.getMovies().add(movie);
        CategoryEntity updatedCategory = categoryRepository.save(category);
        CategoryMoviesListDTO updatedMoviesList = new CategoryMoviesListDTO(updatedCategory);

        serviceLog.updateResponse(updatedMoviesList.toString());
        return updatedMoviesList;
    }

    protected CategoryMoviesListDTO removeMovie(CategoryEntity category, MovieEntity movie, ServiceLog serviceLog){
        serviceLog.setElementName("category");
        serviceLog.info("Removing movie '" + movie.getName() + "' from category '" + category.getName() + "'");

        category.getMovies().remove(movie);
        CategoryEntity updatedCategory = categoryRepository.save(category);
        CategoryMoviesListDTO updatedMoviesList = new CategoryMoviesListDTO(updatedCategory);

        serviceLog.updateResponse(updatedMoviesList.toString());
        return updatedMoviesList;
    }

    protected CategoryTvShowsListDTO addTvShow(CategoryEntity category, TvShowEntity tvShow, ServiceLog serviceLog){
        serviceLog.setElementName("category");
        serviceLog.info("Adding TV show '" + tvShow.getTitle() + "' to category '" + category.getName() + "'");

        category.getTvShows().add(tvShow);
        CategoryEntity updatedCategory = categoryRepository.save(category);
        CategoryTvShowsListDTO updatedTvShowsList = new CategoryTvShowsListDTO(updatedCategory);

        serviceLog.updateResponse(updatedTvShowsList.toString());
        return updatedTvShowsList;
    }

    protected CategoryTvShowsListDTO removeTvShow(CategoryEntity category, TvShowEntity tvShow, ServiceLog serviceLog){
        serviceLog.setElementName("category");
        serviceLog.info("Removing TV show '" + tvShow.getTitle() + "' from category '" + category.getName() + "'");

        category.getTvShows().remove(tvShow);
        CategoryEntity updatedCategory = categoryRepository.save(category);
        CategoryTvShowsListDTO updatedTvShowsList = new CategoryTvShowsListDTO(updatedCategory);

        serviceLog.updateResponse(updatedTvShowsList.toString());
        return updatedTvShowsList;
    }
}
