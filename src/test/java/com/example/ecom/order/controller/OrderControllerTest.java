package com.example.ecom.order.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.ecom.order.exception.OrderNotfoundException;
import com.example.ecom.order.model.FundTransfer;
import com.example.ecom.order.model.OrderDTO;
import com.example.ecom.order.model.OrderLineDTO;
import com.example.ecom.order.service.FundTransferService;
import com.example.ecom.order.service.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
	@InjectMocks
	private OrderController orderController;
	@Mock
	private OrderServiceImpl orderServiceImpl;
	@Mock
	private FundTransferService fundTransferService;
	
	private static OrderDTO order;
	private static OrderDTO order1;
	private static List<OrderDTO> orders;
	
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
		
		orders = new ArrayList<>();
		orders.add(order);
		orders.add(order1);
	}
	
	@Test
	@DisplayName("Get all Orders test")
	void testGetAllOrders() {
		when(orderServiceImpl.getOrders()).thenAnswer(i -> {
			if(orders==null)
				return null;
			return orders;
		});
		
		List<OrderDTO> persistedOrders = orderController.getOrders().getBody();
		assertEquals(orders, persistedOrders);
		
	}
	@Test
	@DisplayName("Get all Orders null test")
	void testGetNoOrders() {
		when(orderServiceImpl.getOrders()).thenAnswer(i -> {
			return null;
		});
		assertEquals(HttpStatus.NO_CONTENT,orderController.getOrders().getStatusCode());
	}
	
	@Test
	@DisplayName("Get Order by id test")
	void testGetOrderById() {
		when(orderServiceImpl.getOrder(any(Long.class))).thenAnswer(i -> {
			Long orderId = i.getArgument(0);
			order.setId(orderId);
			return order;
		});
		
		OrderDTO persistedOrder = orderController.getOrder(2001L).getBody();
		
		assertEquals(order, persistedOrder);
	}
	
	@Test
	@DisplayName("Get no Order by id test")
	void testGetNoOrderById() {
		when(orderServiceImpl.getOrder(any(Long.class))).thenAnswer(i -> {
			return null;
		});
		assertThrows(OrderNotfoundException.class, ()->orderController.getOrder(2001L));
	}
	
	@Test
	@DisplayName("Get Order by id: Negative Scenario")
	void testGetOrderByIdNotFound() {
		//context
		when(orderServiceImpl.getOrder(5L)).thenThrow(OrderNotfoundException.class);
		
		//event
		//outcome
		assertThrows(OrderNotfoundException.class, ()->orderController.getOrder(5L));
	}
	
	@Test
	@DisplayName("Save Order test")
	void testSaveOrder() {
		when(orderServiceImpl.createOrder(any(OrderDTO.class))).thenAnswer(i -> {
			OrderDTO order = i.getArgument(0);
			order.setId(30011L);
			return order;
		});
		
		OrderDTO persistedOrder = orderController.createOrder(order).getBody();
		
		assertEquals(order, persistedOrder);
	}
	
	@Test
	@DisplayName("Order not saved test")
	void testSaveNoOrder() {
		when(orderServiceImpl.createOrder(any(OrderDTO.class))).thenAnswer(i -> {
			return null;
		});
		
		assertEquals(HttpStatus.NO_CONTENT,orderController.createOrder(order).getStatusCode());
	}

	@Test
	@DisplayName("Get Order by user test")
	void testgetOrdersByUser() {
		List<OrderDTO> orders = new ArrayList<>();
		orders.add(order);
		orders.add(order1);
		when(orderServiceImpl.getByUserId(any(Long.class))).thenAnswer(i -> {
			Long userId = i.getArgument(0);
			
			return orders;
		});
		
		List<OrderDTO> persistedOrder = orderController.getOrdersByUserId(10L).getBody();
		assertEquals(orders, persistedOrder);
	}
	@Test
	@DisplayName("Get no Order by user test")
	void testGetNoOrdersByUser() {
		List<OrderDTO> orders = new ArrayList<>();
		orders.add(order);
		orders.add(order1);
		when(orderServiceImpl.getByUserId(any(Long.class))).thenAnswer(i -> {
			return null;
		});
		assertThrows(OrderNotfoundException.class, ()->orderController.getOrdersByUserId(5L));
	}
	@Test
	@DisplayName("Get Order by order date test")
	void testgetOrdersByOrderDate() {
		List<OrderDTO> orders = new ArrayList<>();
		orders.add(order);
		orders.add(order1);
		when(orderServiceImpl.getByOrderDate(any(LocalDate.class))).thenAnswer(i -> {
			LocalDate localDate = i.getArgument(0);
			
			return orders;
		});
		
		List<OrderDTO> persistedOrder = orderController.getOrdersByOrderDate(LocalDate.now()).getBody();
		assertEquals(orders, persistedOrder);
	}
	@Test
	@DisplayName("Get no Order by order date test")
	void testGetNoOrdersByOrderDate() {
		List<OrderDTO> orders = new ArrayList<>();
		orders.add(order);
		orders.add(order1);
		when(orderServiceImpl.getByOrderDate(any(LocalDate.class))).thenAnswer(i -> {
			return null;
		});
		
		assertThrows(OrderNotfoundException.class, ()->orderController.getOrdersByOrderDate(LocalDate.now()));
	}

	@Test
	@DisplayName("checout Order test")
	void testCheckOutOrder() {
		FundTransfer fundTransfer = new FundTransfer();
		fundTransfer.setAmount(order.getTotal());
		fundTransfer.setUserId(order.getUserId());
		fundTransfer.setBeneficaryId(100L);
		
		when(orderServiceImpl.createOrder(any(OrderDTO.class))).thenAnswer(i -> {
			OrderDTO order = i.getArgument(0);
			order.setId(30011L);
			return order;
		});
		
		OrderDTO persistedOrder = orderController.orderCheckOut(order).getBody();
		assertEquals(order,persistedOrder);
	}
	
	@Test
	@DisplayName("checout Order issue test")
	void testCheckOutOrderIssue() {
		FundTransfer fundTransfer = new FundTransfer();
		fundTransfer.setAmount(order.getTotal());
		fundTransfer.setUserId(order.getUserId());
		fundTransfer.setBeneficaryId(100L);
		
		when(orderServiceImpl.createOrder(any(OrderDTO.class))).thenAnswer(i -> {
			return null;
		});
		
		ResponseEntity<OrderDTO> persistedOrder = orderController.orderCheckOut(order);
		assertEquals(HttpStatus.NO_CONTENT, persistedOrder.getStatusCode());
	}
}
