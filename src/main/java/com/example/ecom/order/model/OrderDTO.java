package com.example.ecom.order.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class OrderDTO {
	private Long id;
	private Double total;
	private LocalDate orderDate;
	private Long userId;
	private List<OrderLineDTO> orderLines;
	public OrderDTO() {
	}
	
	public OrderDTO(Long id, Double total, LocalDate orderDate, Long userId, List<OrderLineDTO> orderLines) {
		super();
		this.id = id;
		this.total = total;
		this.orderDate = orderDate;
		this.userId = userId;
		this.orderLines = orderLines;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setOrderLines(List<OrderLineDTO> orderLines) {
		this.orderLines = orderLines;
	}
	public List<OrderLineDTO> getOrderLines() {
		return orderLines;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO order = (OrderDTO) o;
        return id == order.id  && orderDate.equals(order.orderDate)&& total == order.total&&userId == order.userId&&orderLines.equals(orderLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate,total,userId,orderLines);
    }
}
