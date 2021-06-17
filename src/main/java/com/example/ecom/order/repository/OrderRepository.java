package com.example.ecom.order.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecom.order.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	public List<Order> findByUserId(Long userId);
	public List<Order> findByOrderDate(LocalDate orderDate);
	public List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
}
