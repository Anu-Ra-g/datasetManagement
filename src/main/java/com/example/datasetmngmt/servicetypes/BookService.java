package com.example.datasetmngmt.servicetypes;

import com.example.datasetmngmt.model.Book;
import com.example.datasetmngmt.repo.BookRepo;
import com.example.datasetmngmt.service.DatasetHandler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import java.util.*;

@Service("book")
public class BookService implements DatasetHandler {

    private final BookRepo bookRepo;
    private final EntityManager entityManager;

    public BookService(BookRepo bookRepo, EntityManager entityManager) {
        this.bookRepo = bookRepo;
        this.entityManager = entityManager;
    }

    @Override
    public Integer createRecord(Map<String, Object> data) {
        Book book = new Book();
        book.setTitle((String) data.get("title"));
        book.setAuthor((String) data.get("author"));
        book.setGenre((String) data.get("genre"));
        book.setPrice(Double.parseDouble(data.get("price").toString()));

        Book saved = bookRepo.save(book);
        return saved.getId();
    }

    @Override
    public List<Map<String, Object>> query(String groupBy, String sortBy, String order) {
        Set<String> validFields = Set.of("id", "title", "author", "genre", "price");

        if (groupBy != null && !validFields.contains(groupBy)) {
            throw new IllegalArgumentException("Invalid groupBy field: " + groupBy);
        }

        if (sortBy != null && !validFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sortBy field: " + sortBy);
        }

        StringBuilder jpql = new StringBuilder("SELECT b FROM Book b");

        if (sortBy != null && !sortBy.isEmpty()) {
            jpql.append(" ORDER BY b.").append(sortBy).append(" ");
            jpql.append(order != null && order.equalsIgnoreCase("desc") ? "DESC" : "ASC");
        }

        TypedQuery<Book> query = entityManager.createQuery(jpql.toString(), Book.class);
        List<Book> books = query.getResultList();

        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Book b : books) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", b.getId());
            map.put("title", b.getTitle());
            map.put("author", b.getAuthor());
            map.put("genre", b.getGenre());
            map.put("price", b.getPrice());
            resultList.add(map);
        }

        return resultList;
    }

}