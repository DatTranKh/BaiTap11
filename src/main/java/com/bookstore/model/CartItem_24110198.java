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
public class CartItem_24110198 implements Serializable {
    private static final long serialVersionUID = 1L;

    private Book_24110198 book;
    private int quantity;

    public BigDecimal getTotalPrice() {
        if (book == null || book.getPrice() == null) {
            return BigDecimal.ZERO;
        }
        return book.getPrice().multiply(new BigDecimal(quantity));
    }
}