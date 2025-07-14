package com.example.datasetmngmt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.datasetmngmt.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{

}
