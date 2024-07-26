package com.flixsync.utils;

import org.springframework.data.domain.*;

import java.util.List;

public class PageUtils {

    public static <T> Page<T> getPage(List<T> elements, int pageNumber, int pageSize, Sort sort) {
        final Pageable pageRequest = getPageRequest(pageNumber, pageSize, sort);
        final List<T> pageContent = getPageContent(elements, pageRequest);
        return new PageImpl<>(pageContent, pageRequest, elements.size());
    }

    public static Pageable getPageRequest(int pageNumber, int pageSize, Sort sort) {
        if(sort == null)
            return PageRequest.of(pageNumber, pageSize);
        else
            return PageRequest.of(pageNumber, pageSize, sort);
    }

    public static <T> List<T> getPageContent(List<T> allElements, Pageable pageRequest){
        final int startIndex = (int) pageRequest.getOffset();
        int endIndex = startIndex + pageRequest.getPageSize();

        if(endIndex > allElements.size())
            endIndex = allElements.size();

        if(pageRequest.getSort().isUnsorted())
            return allElements.subList(startIndex, endIndex);

        final String sortByProperty = getSortByProperty(pageRequest);
        List<T> allElementsSorted = ListUtils.sortList(allElements, sortByProperty);

        return allElementsSorted.subList(startIndex, endIndex);
    }

    public static String getSortByProperty(Pageable pageRequest){
        final Sort sortRequest = pageRequest.getSort();

        if(sortRequest.getOrderFor("id") != null)
            return "id";
        else if(sortRequest.getOrderFor("episodeId") != null)
            return "episodeId";

        return null;
    }
}
