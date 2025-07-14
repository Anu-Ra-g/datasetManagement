package com.example.datasetmngmt.servicetypes;

import com.example.datasetmngmt.model.Student;
import com.example.datasetmngmt.repo.StudentRepo;
import com.example.datasetmngmt.service.DatasetHandler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import java.util.*;

@Service("student")
public class StudentService implements DatasetHandler {

    private final StudentRepo studentRepo;
    private final EntityManager entityManager;

    public StudentService(StudentRepo studentRepo, EntityManager entityManager) {
        this.studentRepo = studentRepo;
        this.entityManager = entityManager;
    }

    @Override
    public Integer createRecord(Map<String, Object> data) {
        Student student = new Student();
        student.setName((String) data.get("name"));
        student.setGrade(Integer.parseInt(data.get("grade").toString()));
        student.setSection((String) data.get("section"));
        student.setScore(Double.parseDouble(data.get("score").toString()));

        Student saved = studentRepo.save(student);
        return saved.getId(); 
    }

    @Override
    public List<Map<String, Object>> query(String groupBy, String sortBy, String order) {
        Set<String> validFields = Set.of("id", "name", "grade", "section", "score");

        if (groupBy != null && !validFields.contains(groupBy)) {
            throw new IllegalArgumentException("Invalid groupBy field: " + groupBy);
        }

        if (sortBy != null && !validFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sortBy field: " + sortBy);
        }

        StringBuilder jpql = new StringBuilder("SELECT s FROM Student s");

        if (sortBy != null && !sortBy.isEmpty()) {
            jpql.append(" ORDER BY s.").append(sortBy).append(" ");
            jpql.append(order != null && order.equalsIgnoreCase("desc") ? "DESC" : "ASC");
        }

        TypedQuery<Student> query = entityManager.createQuery(jpql.toString(), Student.class);
        List<Student> students = query.getResultList();

        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Student s : students) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", s.getId());
            map.put("name", s.getName());
            map.put("grade", s.getGrade());
            map.put("section", s.getSection());
            map.put("score", s.getScore());
            resultList.add(map);
        }

        return resultList;
    }

}
