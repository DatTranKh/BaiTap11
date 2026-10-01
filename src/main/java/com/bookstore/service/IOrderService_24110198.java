package com.bookstore.service;

import com.bookstore.model.CartItem_24110198;
import com.bookstore.model.Order_24110198;
import java.util.Map;

public interface IOrderService_24110198 {
    int processCheckoutCOD(Order_24110198 order, Map<Integer, CartItem_24110198> cart);
}