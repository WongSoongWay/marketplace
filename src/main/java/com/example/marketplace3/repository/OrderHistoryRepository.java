package com.example.marketplace3.repository;

import com.example.marketplace3.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> findByUserIdOrderByOrderIdDesc(Long userId);
}