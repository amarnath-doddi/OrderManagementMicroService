package com.example.ecom.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecom.order.entity.OrderLine;
@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long>{
	public List<OrderLine> findByOrderId(Long orderId);
}
