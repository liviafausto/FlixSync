package com.flixsync.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceLog {
    private long startTime;
    private final String SERVICE_NAME;
    private final String ELEMENT_NAME;
    private final String ELEMENT_PLURAL;
    private final String CAPITALIZED_ELEMENT_NAME;

    public ServiceLog(String SERVICE_NAME, String ELEMENT_NAME, String ELEMENT_PLURAL){
        this.SERVICE_NAME = "<<" + SERVICE_NAME + ">>";
        this.ELEMENT_NAME = ELEMENT_NAME;
        this.ELEMENT_PLURAL = ELEMENT_PLURAL;
        this.CAPITALIZED_ELEMENT_NAME = ELEMENT_NAME.substring(0,1).toUpperCase() + ELEMENT_NAME.substring(1);
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
        log.info("{} Requesting page {} with {} {}", SERVICE_NAME, pageNumber, amountPerPage, ELEMENT_PLURAL);
    }

    public void pageResponse(Integer numberOfElements){
        log.info("{} {} {} were retrieved", SERVICE_NAME, numberOfElements, ELEMENT_PLURAL);
    }

    public void searchRequest(Integer id){
        log.info("{} Searching for {} - ID: {}", SERVICE_NAME, ELEMENT_NAME, id);
    }

    public void searchResponse(String body){
        log.info("{} {} found --> {}", SERVICE_NAME, CAPITALIZED_ELEMENT_NAME, body);
    }

    public void saveRequest(String body){
        log.info("{} Inserting a new {} in the database --> {}", SERVICE_NAME, ELEMENT_NAME, body);
    }

    public void saveResponse(String body){
        log.info("{} {} inserted --> {}", SERVICE_NAME, CAPITALIZED_ELEMENT_NAME, body);
    }

    public void updateRequest(String attribute, Integer id, String currentValue, String newValue){
        log.info("{} Updating {}'s {} --> {id='{}', current='{}', new='{}'}", SERVICE_NAME, ELEMENT_NAME, attribute, id, currentValue, newValue);
    }

    public void updateResponse(String body){
        log.info("{} {} updated --> {}", SERVICE_NAME, CAPITALIZED_ELEMENT_NAME, body);
    }

    public void deleteRequest(String body){
        log.info("{} Removing {} from the database --> {}", SERVICE_NAME, ELEMENT_NAME, body);
    }

    public void deleteResponse(Integer id){
        log.info("{} {} {} was successfully removed from the database", SERVICE_NAME, CAPITALIZED_ELEMENT_NAME, id);
    }

    public void error(String message){
        log.error("{} {}", SERVICE_NAME, message);
    }

}
