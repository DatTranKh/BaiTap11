package com.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail_24110198 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int orderId;
    private int bookId;
    private BigDecimal price;
    private int quantity;
    private Book_24110198 book;
}