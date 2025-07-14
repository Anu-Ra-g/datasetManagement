package com.example.datasetmngmt.service;

import com.example.datasetmngmt.factory.DatasetServiceFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DatasetService {

    private final DatasetServiceFactory factory;

    public DatasetService(DatasetServiceFactory factory) {
        this.factory = factory;
    }

    public Integer createRecord(String datasetName, Map<String, Object> data) {
        DatasetHandler handler = factory.getService(datasetName);
        return handler.createRecord(data);
    }

    public List<Map<String, Object>> queryRecord(String datasetName, String groupBy, String sortBy, String order) {
        DatasetHandler handler = factory.getService(datasetName);
        return wrapQueryResult(handler.query(groupBy, sortBy, order), groupBy, sortBy);
    }

    private List<Map<String, Object>> wrapQueryResult(List<Map<String, Object>> data, String groupBy, String sortBy) {
        if (groupBy != null && !groupBy.isEmpty()) {
            Map<String, List<Map<String, Object>>> grouped = new LinkedHashMap<>();
            for (Map<String, Object> item : data) {
                String key = String.valueOf(item.get(groupBy));
                grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
            }
            return List.of(Map.of("groupedRecords", grouped));
        } else if (sortBy != null && !sortBy.isEmpty()) {
            return List.of(Map.of("sortedRecords", data));
        } else {
            return List.of(Map.of("records", data));
        }
    }
}
