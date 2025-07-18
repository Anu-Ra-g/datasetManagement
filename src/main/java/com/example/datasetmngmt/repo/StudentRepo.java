package com.example.datasetmngmt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.datasetmngmt.model.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer>{

}
