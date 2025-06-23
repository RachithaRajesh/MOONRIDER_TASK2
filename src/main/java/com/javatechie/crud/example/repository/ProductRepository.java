package com.javatechie.crud.example.repository;

import java.util.List;

import com.javatechie.crud.example.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByName(String name);

    List<Product> findByNameContainingIgnoreCase(String name);
}
