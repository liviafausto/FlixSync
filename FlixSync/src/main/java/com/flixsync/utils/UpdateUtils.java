package com.flixsync.utils;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class UpdateUtils {
    public static boolean parameterIsDifferent(Object newParameter, Object currentParameter){
        return newParameter != null && !newParameter.equals(currentParameter);
    }

    public static boolean parameterIsDifferent(String newString, String currentString){
        return StringUtils.valid(newString) && !newString.equals(currentString);
    }

    public static boolean parameterIsDifferent(Long newMinutes, Duration currentDuration) {
        Duration newDuration = DurationUtils.getDuration(null, newMinutes);
        return parameterIsDifferent(newDuration, currentDuration);
    }

    public static <T> boolean newParameterIsValid(T newParameter, T currentParameter){
        if(newParameter instanceof String s1 && currentParameter instanceof String s2){
            return parameterIsDifferent(s1, s2);
        }
        else if(newParameter instanceof Long min && currentParameter instanceof Duration d){
            return parameterIsDifferent(min, d);
        }
        else return parameterIsDifferent(newParameter, currentParameter);
    }


    public static HashMap<String, Object> adjustParameters(List<String> keys, HashMap<String, Object> currentParams, HashMap<String, Object> newParams){
        HashMap<String, Object> adjustedParams = new HashMap<>();
        boolean newDataProvided = false;

        for(String attribute : keys){
            Object newParam = newParams.get(attribute);
            Object currentParam = currentParams.get(attribute);

            if(newParameterIsValid(newParam, currentParam)){
                adjustedParams.put(attribute, newParam);
                newDataProvided = true;
            } else {
                adjustedParams.put(attribute, currentParam);
            }
        }

        if(!newDataProvided)
            return null;

        return adjustedParams;
    }
}
