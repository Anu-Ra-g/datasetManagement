package com.example.datasetmngmt.controller;

import com.example.datasetmngmt.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/dataset")
public class DatasetController {

    @Autowired
    private DatasetService datasetService;

    @PostMapping("/{datasetName}/record")
    public ResponseEntity<?> createEntry(
            @PathVariable String datasetName,
            @RequestBody Map<String, Object> data) {

        try {
            Integer recordId = datasetService.createRecord(datasetName, data);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Record added successfully");
            response.put("dataset", datasetName + "_dataset");
            response.put("recordId", recordId);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{datasetName}/record")
    public ResponseEntity<?> queryEntry(
            @PathVariable String datasetName,
            @RequestParam(required = false) String groupBy,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order) {

        try {
            List<Map<String, Object>> result = datasetService.queryRecord(datasetName, groupBy, sortBy, order);
        return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_IMPLEMENTED);
        }
    }
}
