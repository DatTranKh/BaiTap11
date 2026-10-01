package com.bookstore.service.impl;

import com.bookstore.dao.OrderDAO_24110198;
import com.bookstore.model.CartItem_24110198;
import com.bookstore.model.Order_24110198;
import com.bookstore.service.IOrderService_24110198;

import java.util.Map;

public class OrderServiceImpl_24110198 implements IOrderService_24110198 {
    private final OrderDAO_24110198 orderDAO = new OrderDAO_24110198();

    @Override
    public int processCheckoutCOD(Order_24110198 order, Map<Integer, CartItem_24110198> cart) {
        return orderDAO.createOrderCOD(order, cart);
    }
}