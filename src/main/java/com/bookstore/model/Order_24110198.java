package com.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order_24110198 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int orderId;
    private Integer userId;
    private String customerName;
    private String customerPhone;
    private String shippingAddress;
    private String note;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private Date orderDate;
    private String status;

    private List<OrderDetail_24110198> orderDetails;
}