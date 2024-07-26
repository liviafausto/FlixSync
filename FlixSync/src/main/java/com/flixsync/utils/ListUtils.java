package com.flixsync.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ListUtils {

    public static <T> List<T> sortList(List<T> elements, String sortByProperty) {
        if(elements == null || elements.isEmpty())
            return elements;

        Class<?> elementClassType = elements.get(0).getClass();
        Comparator<T> comparator = ComparatorUtils.getComparatorForClass(elementClassType, sortByProperty);

        if(comparator != null){
            List<T> sortedList = new ArrayList<>(elements);
            sortedList.sort(comparator);
            return sortedList;
        }

        return null;
    }

    public static <T> List<T> getSortedList(Set<T> elements, String sortByProperty) {
        return sortList(elements.stream().toList(), sortByProperty);
    }

}
