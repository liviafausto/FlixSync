package com.flixsync.utils;

import java.time.Duration;

public class MovieDuration {
    public static Long getHours(Duration duration){
        if(duration == null) return null;
        else return duration.getSeconds() / 3600;
    }

    public static Long getMinutes(Duration duration){
        if(duration == null) return null;
        else return (duration.getSeconds() % 3600) / 60;
    }

    public static Duration getDuration(Long hours, Long minutes){
        if(hours == null && minutes == null) return null;
        if(hours == null) hours = 0L;
        if(minutes == null) minutes = 0L;

        Duration duration = Duration.ofHours(hours);
        return duration.plusMinutes(minutes);
    }

    public static String format(Duration duration){
        if(duration == null) return null;
        else return getHours(duration) + " hours " + getMinutes(duration) + " minutes";
    }
}
