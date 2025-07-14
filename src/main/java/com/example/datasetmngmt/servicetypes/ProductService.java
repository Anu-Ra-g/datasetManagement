package com.example.datasetmngmt.servicetypes;

import com.example.datasetmngmt.model.Product;
import com.example.datasetmngmt.repo.ProductRepo;
import com.example.datasetmngmt.service.DatasetHandler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import java.util.*;

@Service("product")
public class ProductService implements DatasetHandler {

    private final ProductRepo productRepo;
    private final EntityManager entityManager;

    public ProductService(ProductRepo productRepo, EntityManager entityManager) {
        this.productRepo = productRepo;
        this.entityManager = entityManager;
    }

    @Override
    public Integer createRecord(Map<String, Object> data) {
        Product product = new Product();
        product.setName((String) data.get("name"));
        product.setCategory(Integer.parseInt(data.get("category").toString()));
        product.setPrice(Double.parseDouble(data.get("price").toString()));
        product.setBrand((String) data.get("brand"));

        Product saved = productRepo.save(product);
        return saved.getId();
    }

    @Override
    public List<Map<String, Object>> query(String groupBy, String sortBy, String order) {
        Set<String> validFields = Set.of("id", "name", "category", "price", "brand");

        if (groupBy != null && !validFields.contains(groupBy)) {
            throw new IllegalArgumentException("Invalid groupBy field: " + groupBy);
        }

        if (sortBy != null && !validFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sortBy field: " + sortBy);
        }

        StringBuilder jpql = new StringBuilder("SELECT p FROM Product p");

        if (sortBy != null && !sortBy.isEmpty()) {
            jpql.append(" ORDER BY p.").append(sortBy).append(" ");
            jpql.append(order != null && order.equalsIgnoreCase("desc") ? "DESC" : "ASC");
        }

        TypedQuery<Product> query = entityManager.createQuery(jpql.toString(), Product.class);
        List<Product> products = query.getResultList();

        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Product p : products) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", p.getId());
            map.put("name", p.getName());
            map.put("category", p.getCategory());
            map.put("price", p.getPrice());
            map.put("brand", p.getBrand());
            resultList.add(map);
        }

        return resultList;
    }

}
