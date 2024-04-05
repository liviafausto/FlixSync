package com.flixsync.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
public class ServiceLog {
    private long startTime;
    private String elementName;
    private final String SERVICE_NAME;

    public ServiceLog(String SERVICE_NAME, String elementName){
        this.SERVICE_NAME = "<<" + SERVICE_NAME + ">>";
        this.elementName = elementName;
    }

    public void start(String description){
        startTime = System.currentTimeMillis();
        log.info("{} Service started: {}", SERVICE_NAME, description);
    }

    public void end(){
        long endTime = System.currentTimeMillis();
        log.info("{} End of service --> Time: {}ms", SERVICE_NAME, endTime-startTime);
    }

    public void pageRequest(Integer pageNumber, Integer amountPerPage){
        log.info("{} Requesting page {} with {} elements", SERVICE_NAME, pageNumber, amountPerPage);
    }

    public void pageResponse(Integer numberOfElements){
        log.info("{} {} elements were retrieved", SERVICE_NAME, numberOfElements);
    }

    public void searchRequest(Integer id){
        log.info("{} Searching for {} - ID: {}", SERVICE_NAME, elementName, id);
    }

    public void searchResponse(String body){
        log.info("{} {} found --> {}", SERVICE_NAME, capitalizedElementName(), body);
    }

    public void saveRequest(String body){
        log.info("{} Inserting a new {} in the database --> {}", SERVICE_NAME, elementName, body);
    }

    public void saveResponse(String body){
        log.info("{} {} inserted --> {}", SERVICE_NAME, capitalizedElementName(), body);
    }

    public void updateRequest(String attribute, Integer id, String currentValue, String newValue){
        log.info("{} Updating {}'s {} --> {id='{}', current='{}', new='{}'}", SERVICE_NAME, elementName, attribute, id, currentValue, newValue);
    }

    public void updateResponse(String body){
        log.info("{} {} updated --> {}", SERVICE_NAME, capitalizedElementName(), body);
    }

    public void deleteRequest(String body){
        log.info("{} Removing {} from the database --> {}", SERVICE_NAME, elementName, body);
    }

    public void deleteResponse(Integer id){
        log.info("{} {} {} was successfully removed from the database", SERVICE_NAME, capitalizedElementName(), id);
    }

    public void info(String message){
        log.info("{} {}", SERVICE_NAME, message);
    }

    public void error(String message){
        log.error("{} {}", SERVICE_NAME, message);
    }

    private String capitalizedElementName(){
        return elementName.substring(0,1).toUpperCase() + elementName.substring(1);
    }

}
