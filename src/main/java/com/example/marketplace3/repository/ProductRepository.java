package com.example.marketplace3.repository;

import com.example.marketplace3.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByVisibleTrue();
}
