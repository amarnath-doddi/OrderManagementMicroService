package com.example.ecom.order.model;

import java.util.Objects;

public class OrderLineDTO {
	private Long id;
	private OrderDTO order;
	private Long productId;
	private Double price;
	private Long quantity;
	public OrderLineDTO() {
	}
	
	public OrderLineDTO(Long id, OrderDTO order, Long productId, Double price, Long quantity) {
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
	public OrderDTO getOrder() {
		return order;
	}
	public void setOrder(OrderDTO order) {
		this.order = order;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineDTO orderLine = (OrderLineDTO) o;
        return id == orderLine.id  &&  order == orderLine.order&&productId == orderLine.productId&&price==orderLine.price&&quantity==orderLine.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order,productId,price,quantity);
    }
	
}
