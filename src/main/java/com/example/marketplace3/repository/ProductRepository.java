package com.example.marketplace3.repository;

import com.example.marketplace3.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product, Long> {
}
