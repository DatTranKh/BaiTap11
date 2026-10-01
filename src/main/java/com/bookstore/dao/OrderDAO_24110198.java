package com.bookstore.dao;

import com.bookstore.connection.DBContext_24110198;
import com.bookstore.model.CartItem_24110198;
import com.bookstore.model.Order_24110198;

import java.sql.*;
import java.util.Map;

public class OrderDAO_24110198 {

    public int createOrderCOD(Order_24110198 order, Map<Integer, CartItem_24110198> cart) {
        String insertOrderSql = "INSERT INTO dbo.orders (user_id, customer_name, customer_phone, shipping_address, note, total_amount, payment_method, order_date, status) " +
                                "VALUES (?, ?, ?, ?, ?, ?, 'COD', GETDATE(), N'Chờ xác nhận')";
        String insertDetailSql = "INSERT INTO dbo.order_details (order_id, bookid, price, quantity) VALUES (?, ?, ?, ?)";
        String updateStockSql = "UPDATE dbo.books SET quantity = quantity - ? WHERE bookid = ? AND quantity >= ?";

        Connection conn = null;
        int generatedOrderId = -1;

        try {
            conn = DBContext_24110198.getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            // 1. Tạo bản ghi đơn hàng
            try (PreparedStatement psOrder = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
                if (order.getUserId() != null) {
                    psOrder.setInt(1, order.getUserId());
                } else {
                    psOrder.setNull(1, Types.INTEGER);
                }
                psOrder.setString(2, order.getCustomerName());
                psOrder.setString(3, order.getCustomerPhone());
                psOrder.setString(4, order.getShippingAddress());
                psOrder.setString(5, order.getNote());
                psOrder.setBigDecimal(6, order.getTotalAmount());
                psOrder.executeUpdate();

                try (ResultSet rs = psOrder.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedOrderId = rs.getInt(1);
                    }
                }
            }

            if (generatedOrderId <= 0) {
                conn.rollback();
                return -1;
            }

            // 2. Thêm từng chi tiết sản phẩm và trừ tồn kho
            try (PreparedStatement psDetail = conn.prepareStatement(insertDetailSql);
                 PreparedStatement psStock = conn.prepareStatement(updateStockSql)) {

                for (CartItem_24110198 item : cart.values()) {
                    // Thêm chi tiết
                    psDetail.setInt(1, generatedOrderId);
                    psDetail.setInt(2, item.getBook().getBookid());
                    psDetail.setBigDecimal(3, item.getBook().getPrice());
                    psDetail.setInt(4, item.getQuantity());
                    psDetail.executeUpdate();

                    // Cập nhật tồn kho (kiểm tra tồn kho >= số lượng mua)
                    psStock.setInt(1, item.getQuantity());
                    psStock.setInt(2, item.getBook().getBookid());
                    psStock.setInt(3, item.getQuantity());
                    int rowsUpdated = psStock.executeUpdate();

                    if (rowsUpdated == 0) {
                        // Số lượng tồn kho không đủ để trừ
                        conn.rollback();
                        return -2;
                    }
                }
            }

            conn.commit();
            return generatedOrderId;

        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return -1;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
    }
}