package com.bookstore.controller;

import com.bookstore.model.CartItem_24110198;
import com.bookstore.model.Order_24110198;
import com.bookstore.model.User_24110198;
import com.bookstore.service.IOrderService_24110198;
import com.bookstore.service.impl.OrderServiceImpl_24110198;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@WebServlet(urlPatterns = {"/checkout", "/order-success"})
public class CheckoutController_24110198 extends HttpServlet {
    private final IOrderService_24110198 orderService = new OrderServiceImpl_24110198();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/order-success".equals(path)) {
            req.getRequestDispatcher("/WEB-INF/views/web/order_success.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Integer, CartItem_24110198> cart = (Map<Integer, CartItem_24110198>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        BigDecimal grandTotal = BigDecimal.ZERO;
        for (CartItem_24110198 item : cart.values()) {
            grandTotal = grandTotal.add(item.getTotalPrice());
        }

        req.setAttribute("grandTotal", grandTotal);
        req.getRequestDispatcher("/WEB-INF/views/web/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Integer, CartItem_24110198> cart = (Map<Integer, CartItem_24110198>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        User_24110198 user = (User_24110198) session.getAttribute("user");

        String customerName = req.getParameter("customerName");
        String customerPhone = req.getParameter("customerPhone");
        String shippingAddress = req.getParameter("shippingAddress");
        String note = req.getParameter("note");

        BigDecimal grandTotal = BigDecimal.ZERO;
        for (CartItem_24110198 item : cart.values()) {
            grandTotal = grandTotal.add(item.getTotalPrice());
        }

        Order_24110198 order = Order_24110198.builder()
                .userId(user != null ? user.getId() : null)
                .customerName(customerName)
                .customerPhone(customerPhone)
                .shippingAddress(shippingAddress)
                .note(note)
                .totalAmount(grandTotal)
                .paymentMethod("COD")
                .build();

        int resultOrderId = orderService.processCheckoutCOD(order, cart);

        if (resultOrderId > 0) {
            // Xóa sạch giỏ hàng khi đặt hàng thành công
            session.removeAttribute("cart");
            resp.sendRedirect(req.getContextPath() + "/order-success?orderId=" + resultOrderId);
        } else if (resultOrderId == -2) {
            req.setAttribute("errorMessage", "Một số sản phẩm trong kho không đủ số lượng cung ứng. Vui lòng cập nhật lại giỏ hàng!");
            req.setAttribute("grandTotal", grandTotal);
            req.getRequestDispatcher("/WEB-INF/views/web/checkout.jsp").forward(req, resp);
        } else {
            req.setAttribute("errorMessage", "Đã có lỗi xảy ra trong quá trình tạo đơn hàng COD. Vui lòng thử lại!");
            req.setAttribute("grandTotal", grandTotal);
            req.getRequestDispatcher("/WEB-INF/views/web/checkout.jsp").forward(req, resp);
        }
    }
}