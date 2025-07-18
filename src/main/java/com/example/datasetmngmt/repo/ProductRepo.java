package com.example.datasetmngmt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.datasetmngmt.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{

}
