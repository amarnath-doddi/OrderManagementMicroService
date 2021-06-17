package com.example.ecom.order.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.order.exception.OrderNotfoundException;
import com.example.ecom.order.model.FundTransfer;
import com.example.ecom.order.model.OrderDTO;
import com.example.ecom.order.service.FundTransferService;
import com.example.ecom.order.service.OrderService;
@RestController
public class OrderController {
	Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private FundTransferService fundTransferService;
	
	@GetMapping("/")
	public ResponseEntity<List<OrderDTO>> getOrders(){
		List<OrderDTO> orders = orderService.getOrders();
		if(orders==null) {
			logger.info("No orders exist in the system.");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id){
		OrderDTO order = orderService.getOrder(id);
		if(order==null) {
			String message = String.format("Order doesn't exist with id :%s",id); 
			logger.error(message);
			throw new OrderNotfoundException(message);
		}
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order){
		OrderDTO createdOrder = orderService.createOrder(order);
		if(createdOrder==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(createdOrder,HttpStatus.OK);
	} 
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable Long userId){
		List<OrderDTO> orders = orderService.getByUserId(userId);
		if(orders==null) {
			String message = String.format("Orders doesn't exist for user :%s",userId);
			throw new OrderNotfoundException(message);
		}
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
	
	@GetMapping("/date/{orderDate}")
	public ResponseEntity<List<OrderDTO>> getOrdersByOrderDate(@PathVariable LocalDate orderDate){
		List<OrderDTO> orders = orderService.getByOrderDate(orderDate);
		if(orders==null) {
			String message = String.format("Orders doesn't exist for order date :%s",orderDate);
			throw new OrderNotfoundException(message);
		}
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
	@GetMapping("/date/{startDate}/{endDate}")
	public ResponseEntity<List<OrderDTO>> getOrdersByOrderDateBetween(@PathVariable LocalDate startDate,@PathVariable LocalDate endDate){
		List<OrderDTO> orders = orderService.getByOrderDateBetween(startDate,endDate);
		if(orders==null) {
			String message = String.format("Orders doesn't exist for order between dates :%s and %s",startDate,endDate);
			throw new OrderNotfoundException(message);
		}
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
	
	@PostMapping("/order/checkout/")
	public ResponseEntity<OrderDTO> orderCheckOut(@RequestBody OrderDTO order){
		BiFunction<Double,Long,Double> olderLineTotalAmount = (price,quantity) -> price * quantity;
		Double total = order.getOrderLines().stream().map(orderLine -> olderLineTotalAmount.apply(orderLine.getPrice(), orderLine.getQuantity())).reduce(0.0,Double::sum);
		order.setTotal(total);
		OrderDTO savedOrder = orderService.createOrder(order);
		FundTransfer fundTransfer = new FundTransfer();
		fundTransfer.setAmount(total);
		fundTransfer.setUserId(order.getUserId());
		fundTransfer.setBeneficaryId(100L);
		fundTransferService.transfer(fundTransfer);
		if(savedOrder==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(savedOrder,HttpStatus.OK);
	} 

}
