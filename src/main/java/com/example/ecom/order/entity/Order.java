package com.example.ecom.order.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "total")
	private Double total;
	@Column(name = "order_date")
	private LocalDate orderDate;
	@Column(name = "user_id")
	private Long userId;
	@OneToMany(targetEntity = OrderLine.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	private List<OrderLine> orderLines;
	public Order() {
	}
	
	public Order(Long id, LocalDate orderDate) {
		super();
		this.id = id;
		this.orderDate = orderDate;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getTotal() {
		return total;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	public List<OrderLine> getOrderLines() {
		return orderLines;
	}
}
