package com.example.datasetmngmt.factory;

import com.example.datasetmngmt.service.DatasetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DatasetServiceFactory {

    @Autowired
    private ApplicationContext context;

    public DatasetHandler getService(String datasetName) {
        try {
            return (DatasetHandler) context.getBean(datasetName.toLowerCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("No service found for dataset: " + datasetName);
        }
    }
}
