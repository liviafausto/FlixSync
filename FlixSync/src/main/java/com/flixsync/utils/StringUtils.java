package com.flixsync.utils;

public class StringUtils {
    public static boolean valid(String str) {
        return str != null && !str.isEmpty() && !str.isBlank();
    }

    public static String firstLetterToUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
