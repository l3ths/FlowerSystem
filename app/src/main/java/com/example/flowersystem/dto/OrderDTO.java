package com.example.flowersystem.dto;


import java.io.Serializable;
import java.time.LocalDateTime;

public class OrderDTO implements Serializable {

    private Long id;
    private String orderDate;
    private String shippingDate;
    private String orderStatus;
    private double total;
    private String paymentMethod;
    private PrepaidOrderDTO prepaidOrderDTO;

    public OrderDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(String shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PrepaidOrderDTO getPrepaidOrderDTO() {
        return prepaidOrderDTO;
    }

    public void setPrepaidOrderDTO(PrepaidOrderDTO prepaidOrderDTO) {
        this.prepaidOrderDTO = prepaidOrderDTO;
    }
}
