package com.example.datasetmngmt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.datasetmngmt.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

   
}
