package com.example.datasetmngmt.servicetypes;

import com.example.datasetmngmt.model.Employee;
import com.example.datasetmngmt.repo.EmployeeRepo;
import com.example.datasetmngmt.service.DatasetHandler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import java.util.*;

@Service("employee")
public class EmployeeService implements DatasetHandler {

    private final EmployeeRepo employeeRepo;
    private final EntityManager entityManager;

    public EmployeeService(EmployeeRepo employeeRepo, EntityManager entityManager) {
        this.employeeRepo = employeeRepo;
        this.entityManager = entityManager;
    }

    @Override
    public Integer createRecord(Map<String, Object> data) {
        Employee employee = new Employee();
        employee.setName((String) data.get("name"));
        employee.setAge(Integer.parseInt(data.get("age").toString()));
        employee.setDepartment((String) data.get("department"));
        Employee saved = employeeRepo.save(employee);
        return saved.getId();
    }

    @Override
    public List<Map<String, Object>> query(String groupBy, String sortBy, String order) {
        Set<String> validFields = Set.of("id", "name", "age", "department");

        if (groupBy != null && !validFields.contains(groupBy)) {
            throw new IllegalArgumentException("Invalid groupBy field: " + groupBy);
        }

        if (sortBy != null && !validFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sortBy field: " + sortBy);
        }

        StringBuilder jpql = new StringBuilder("SELECT e FROM Employee e");

        if (sortBy != null && !sortBy.isEmpty()) {
            jpql.append(" ORDER BY e.").append(sortBy).append(" ");
            jpql.append(order != null && order.equalsIgnoreCase("desc") ? "DESC" : "ASC");
        }

        TypedQuery<Employee> query = entityManager.createQuery(jpql.toString(), Employee.class);
        List<Employee> employees = query.getResultList();

        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Employee e : employees) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", e.getId());
            map.put("name", e.getName());
            map.put("age", e.getAge());
            map.put("department", e.getDepartment());
            resultList.add(map);
        }

        return resultList;
    }

}
