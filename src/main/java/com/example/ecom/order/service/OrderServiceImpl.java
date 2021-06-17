package com.example.ecom.order.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecom.order.entity.Order;
import com.example.ecom.order.entity.OrderLine;
import com.example.ecom.order.model.OrderDTO;
import com.example.ecom.order.model.OrderLineDTO;
import com.example.ecom.order.repository.OrderLineRepository;
import com.example.ecom.order.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderLineRepository orderLineRepository;
	@Override
	public OrderDTO updateOrder(OrderDTO order) {
		return getOrderDTO(orderRepository.saveAndFlush(getOrder(order)));
	}
	@Override
	public List<OrderDTO> getOrders() {
		return orderRepository.findAll().stream().map(bf -> getOrderDTO(bf)).collect(Collectors.toList());
	}
	@Override
	public OrderDTO getOrder(Long id) {
		return getOrderDTO(orderRepository.findById(id).orElse(new Order()));
	}
	@Override
	public OrderDTO createOrder(OrderDTO order) {
		return getOrderDTO(orderRepository.saveAndFlush(getOrder(order)));
	}
	@Override
	public List<OrderDTO> getByUserId(Long userId) {
		return orderRepository.findByUserId(userId).stream().map(order -> getOrderDTO(order)).collect(Collectors.toList());
	}
	@Override
	public List<OrderDTO> getByOrderDate(LocalDate localDate) {
		return orderRepository.findByOrderDate(localDate).stream().map(order -> getOrderDTO(order)).collect(Collectors.toList());
	}
	
	private OrderDTO getOrderDTO(Order order) {
		List<OrderLineDTO> orderLines = order.getOrderLines().stream().map(orderLine -> new OrderLineDTO(orderLine.getId(),new OrderDTO(),orderLine.getProductId(),orderLine.getPrice(),orderLine.getQuantity())).collect(Collectors.toList());
		return new OrderDTO(order.getId(), order.getTotal(), order.getOrderDate(), order.getUserId(),orderLines);
	}
	
	public Order getOrder(OrderDTO orderDto) {
		Order order = new Order();
		order.setId(orderDto.getId());
		order.setOrderDate(orderDto.getOrderDate());
		order.setTotal(orderDto.getTotal());
		order.setUserId(orderDto.getUserId());
		order.setOrderLines(orderDto.getOrderLines().stream().map(orderLine -> new OrderLine(orderLine.getId(),order,orderLine.getProductId(),orderLine.getPrice(),orderLine.getQuantity())).collect(Collectors.toList()));
		return order;
	}
	@Override
	public List<OrderDTO> getByOrderDateBetween(LocalDate startDate, LocalDate endDate) {
		return orderRepository.findByOrderDateBetween(startDate, endDate).stream().map(order -> getOrderDTO(order)).collect(Collectors.toList());
	}
}
