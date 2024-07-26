package com.flixsync.utils;

import com.flixsync.model.entity.*;

import java.util.Comparator;

public class ComparatorUtils {

    @SuppressWarnings("unchecked")
    public static <T> Comparator<T> getComparatorForClass(Class<?> classType, String fieldName){
        if(!StringUtils.valid(fieldName))
            return null;

        final boolean COMPARE_BY_ID = StringUtils.containsIgnoreCase(fieldName, "ID");
        final boolean COMPARE_BY_NAME = fieldName.equalsIgnoreCase("NAME");

        if(classType.equals(MovieEntity.class) && COMPARE_BY_ID)
            return (Comparator<T>) compareMoviesById();

        else if(classType.equals(CategoryEntity.class)){
            if(COMPARE_BY_ID)
                return (Comparator<T>) compareCategoriesById();
            else if(COMPARE_BY_NAME)
                return (Comparator<T>) compareCategoriesByName();
            else return null;
        }

        else if(classType.equals(TvShowEntity.class) && COMPARE_BY_ID)
            return (Comparator<T>) compareTvShowById();

        else if(classType.equals(EpisodeEntity.class) && COMPARE_BY_ID)
            return (Comparator<T>) compareEpisodeById();

        return null;
    }

    private static Comparator<MovieEntity> compareMoviesById(){
        return Comparator.comparing(MovieEntity::getId);
    }

    private static Comparator<CategoryEntity> compareCategoriesById(){
        return Comparator.comparing(CategoryEntity::getId);
    }

    private static Comparator<CategoryEntity> compareCategoriesByName(){
        return Comparator.comparing(CategoryEntity::getName);
    }

    private static Comparator<TvShowEntity> compareTvShowById(){
        return Comparator.comparing(TvShowEntity::getId);
    }

    private static Comparator<EpisodeEntity> compareEpisodeById(){
        return Comparator.comparing(EpisodeEntity::getEpisodeId);
    }

}
