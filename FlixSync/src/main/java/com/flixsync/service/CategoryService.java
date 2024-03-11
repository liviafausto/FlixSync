package com.flixsync.service;

import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.model.dto.category.CategoryOutputDTO;
import com.flixsync.model.entity.CategoryEntity;
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
    private final String CATEGORY = "category";
    private final String CATEGORIES = "categories";

    public Page<CategoryOutputDTO> findAll(Integer pageNumber, Integer amountPerPage){
        ServiceLog serviceLog = new ServiceLog("CATEGORY-FIND-ALL", CATEGORY, CATEGORIES);
        serviceLog.start("Find all categories");

        serviceLog.pageRequest(pageNumber, amountPerPage);
        Page<CategoryEntity> categoriesPage = categoryRepository.findAll(PageRequest.of(pageNumber, amountPerPage, Sort.by("id")));
        serviceLog.pageResponse(categoriesPage.getNumberOfElements());

        serviceLog.end();
        return categoriesPage.map(CategoryOutputDTO::new);
    }

    public CategoryOutputDTO findById(Integer categoryId) throws EntityNotFoundException {
        ServiceLog serviceLog = new ServiceLog("CATEGORY-FIND-BY-ID", CATEGORY, CATEGORIES);
        serviceLog.start("Find a category by id");

        CategoryEntity category = getCategoryById(categoryId, serviceLog);

        serviceLog.end();
        return new CategoryOutputDTO(category);
    }

    public CategoryOutputDTO save(String name){
        ServiceLog serviceLog = new ServiceLog("CATEGORY-SAVE", CATEGORY, CATEGORIES);
        serviceLog.start("Register a category");

        serviceLog.saveRequest("{name: '" + name + "'}");
        CategoryEntity category = new CategoryEntity(name);
        CategoryEntity createdCategory = categoryRepository.save(category);
        serviceLog.saveResponse(createdCategory.toString());

        serviceLog.end();
        return new CategoryOutputDTO(createdCategory);
    }

    public CategoryOutputDTO update(Integer categoryId, String name) throws EntityNotFoundException {
        ServiceLog serviceLog = new ServiceLog("CATEGORY-UPDATE-NAME", CATEGORY, CATEGORIES);
        serviceLog.start("Update a category by id");

        CategoryEntity category = getCategoryById(categoryId, serviceLog);
        serviceLog.updateRequest("name", categoryId, category.getName(), name);
        category.setName(name);
        CategoryEntity updatedCategory = categoryRepository.save(category);
        serviceLog.updateResponse(updatedCategory.toString());

        serviceLog.end();
        return new CategoryOutputDTO(updatedCategory);
    }

    public void delete(Integer categoryId) throws EntityNotFoundException {
        ServiceLog serviceLog = new ServiceLog("CATEGORY-DELETE", CATEGORY, CATEGORIES);
        serviceLog.start("Remove a category by id");

        CategoryEntity category = getCategoryById(categoryId, serviceLog);
        serviceLog.deleteRequest(category.toString());
        categoryRepository.delete(category);
        serviceLog.deleteResponse(categoryId);

        serviceLog.end();
    }

    private CategoryEntity getCategoryById(Integer categoryId, ServiceLog serviceLog) throws EntityNotFoundException {
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
}
