package com.example.ecom.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="order_line")
public class OrderLine {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@ManyToOne
    @JoinColumn(name="order_id")
	private Order order;
	@Column(name = "product_id")
	private Long productId;
	@Column(name = "price")
	private Double price;
	@Column(name = "quantity")
	private Long quantity;
	public OrderLine() {
	}
	
	public OrderLine(Long id, Order order, Long productId, Double price, Long quantity) {
		super();
		this.id = id;
		this.order = order;
		this.productId = productId;
		this.price = price;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getPrice() {
		return price;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
}
