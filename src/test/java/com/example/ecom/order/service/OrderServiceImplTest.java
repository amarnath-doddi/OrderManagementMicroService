package com.example.ecom.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ecom.order.entity.Order;
import com.example.ecom.order.model.OrderDTO;
import com.example.ecom.order.model.OrderLineDTO;
import com.example.ecom.order.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
	@Mock
	private OrderRepository orderRepository;
	@InjectMocks
	private OrderServiceImpl orderServiceImpl;
	
	private static OrderDTO order;
	private static OrderDTO order1;
	
	@BeforeAll
	public static void setUp() {
		
		List<OrderLineDTO> orderLines = new ArrayList<>();
		orderLines.add(new OrderLineDTO(2001L, order, 2001L, 500.00, 5L));
		orderLines.add(new OrderLineDTO(2002L, order, 2010L, 402.00, 2L));
		order = new OrderDTO();
		order.setId(1000L);
		order.setOrderLines(orderLines);
		order.setTotal(902.00);
		order.setUserId(10L);
		order.setOrderDate(LocalDate.now());
		
		List<OrderLineDTO> orderLines1 = new ArrayList<>();
		orderLines1.add(new OrderLineDTO(2003L, order1, 2005L, 55.00, 1L));
		orderLines1.add(new OrderLineDTO(2004L, order1, 2006L, 54.00, 1L));
		orderLines1.add(new OrderLineDTO(2005L, order1, 2007L, 12.00, 1L));
		orderLines1.add(new OrderLineDTO(2006L, order1, 2008L, 65.00, 1L));
		orderLines1.add(new OrderLineDTO(2007L, order1, 2009L, 145.00, 1L));
		order1 = new OrderDTO();
		order1.setId(1001L);
		order1.setOrderLines(orderLines1);
		order1.setTotal(331.00);
		order1.setUserId(10L);
		order1.setOrderDate(LocalDate.now());
	}
	
	@Test
	@DisplayName("Test Create Order")
	void testCreateOrder() {
		when(orderRepository.saveAndFlush(any(Order.class))).thenAnswer(i -> {
			Order order = i.getArgument(0);
			order.setId(1000L);
			return order;
		});
		
		OrderDTO savedOrder = orderServiceImpl.createOrder(order);
		
		assertEquals(1000L, savedOrder.getId());
	}
	
	@Test
	@DisplayName("Update Order Test")
	void testUpdateOrder() {
		when(orderRepository.saveAndFlush(any(Order.class))).thenAnswer(i -> {
			Order order = i.getArgument(0);
			order.setId(1000L);
			order.setTotal(100.00);
			return order;
		});
		OrderDTO updatedOrder = orderServiceImpl.updateOrder(order);
		assertEquals(100.0, updatedOrder.getTotal());
	}
	@Test
	@DisplayName("Test getOrders")
	void testgetOrders() {
		List<OrderDTO> products =  orderServiceImpl.getOrders();
		assertNotNull(products);
	}
	@Test
	@DisplayName("Test getOrder")
	void testGetOrder() {
		when(orderRepository.findById(1000L)).thenAnswer(i -> {
			Long id = i.getArgument(0);
			order.setId(id);
			return Optional.ofNullable(orderServiceImpl.getOrder(order));
		});
		OrderDTO productDTO =  orderServiceImpl.getOrder(1000L);
		assertNotNull(productDTO);
	}
	@Test
	@DisplayName("Test getByUserId")
	void testGetByUserId() {
		List<OrderDTO> products =  orderServiceImpl.getByUserId(10L);
		assertNotNull(products);
	}
	@Test
	@DisplayName("Test getByOrderDate")
	void testGetByOrderDate() {
		LocalDate date = LocalDate.now();
		when(orderRepository.findByOrderDate(any(LocalDate.class))).thenAnswer(i -> {
			LocalDate date1 = i.getArgument(0);
			order.setOrderDate(date1);
			List<Order> products = new ArrayList<>();
			products.add(orderServiceImpl.getOrder(order));
			return products;
		});
		List<OrderDTO> products =  orderServiceImpl.getByOrderDate(date);
		assertNotNull(products);
	}
	
	@Test
	@DisplayName("Test getByOrderDateBetween")
	void testGetByOrderDateBetween() {
		LocalDate endDate = LocalDate.now();
		LocalDate startDate = LocalDate.now().minusDays(10);
		when(orderRepository.findByOrderDateBetween(any(LocalDate.class),any(LocalDate.class))).thenAnswer(i -> {
			LocalDate date1 = i.getArgument(0);
			order.setOrderDate(date1);
			List<Order> products = new ArrayList<>();
			products.add(orderServiceImpl.getOrder(order));
			return products;
		});
		List<OrderDTO> products =  orderServiceImpl.getByOrderDateBetween(startDate,endDate);
		assertNotNull(products);
	}

	
}
