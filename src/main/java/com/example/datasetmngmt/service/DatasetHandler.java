package com.example.datasetmngmt.service;

import java.util.List;
import java.util.Map;

public interface DatasetHandler {
    Integer createRecord(Map<String, Object> data);
    List<Map<String, Object>> query(String groupBy, String sortBy, String order);
}
