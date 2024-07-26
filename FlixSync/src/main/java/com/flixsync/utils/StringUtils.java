package com.flixsync.utils;

public class StringUtils {
    public static boolean valid(String str) {
        return str != null && !str.isEmpty() && !str.isBlank();
    }

    public static String firstLetterToUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static boolean containsIgnoreCase(String str, String search) {
        if (str == null || search == null)
            return false;

        return str.toLowerCase().contains(search.toLowerCase());
    }
}
