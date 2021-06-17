package com.example.ecom.order.service;

import java.time.LocalDate;
import java.util.List;

import com.example.ecom.order.model.OrderDTO;

public interface OrderService {
	public OrderDTO updateOrder(OrderDTO order);
	public List<OrderDTO> getOrders();
	public OrderDTO createOrder(OrderDTO order);
	public OrderDTO getOrder(Long id);
	public List<OrderDTO> getByUserId(Long userId);
	public List<OrderDTO> getByOrderDate(LocalDate localDate);
	public List<OrderDTO> getByOrderDateBetween(LocalDate startDate, LocalDate endDate);
}
