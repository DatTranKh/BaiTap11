package com.bookstore.controller;

import com.bookstore.model.Book_24110198;
import com.bookstore.model.CartItem_24110198;
import com.bookstore.service.IBookService_24110198;
import com.bookstore.service.impl.BookServiceImpl_24110198;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/cart", "/cart-add", "/cart-update", "/cart-delete"})
public class CartController_24110198 extends HttpServlet {
    private final IBookService_24110198 bookService = new BookServiceImpl_24110198();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Integer, CartItem_24110198> cart = (Map<Integer, CartItem_24110198>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        if ("/cart-delete".equals(path)) {
            String idStr = req.getParameter("id");
            if (idStr != null) {
                cart.remove(Integer.parseInt(idStr.trim()));
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        // Tính tổng tiền toàn bộ giỏ
        BigDecimal grandTotal = BigDecimal.ZERO;
        for (CartItem_24110198 item : cart.values()) {
            grandTotal = grandTotal.add(item.getTotalPrice());
        }

        req.setAttribute("grandTotal", grandTotal);
        req.getRequestDispatcher("/WEB-INF/views/web/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Integer, CartItem_24110198> cart = (Map<Integer, CartItem_24110198>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        if ("/cart-add".equals(path)) {
            int bookId = Integer.parseInt(req.getParameter("bookid"));
            int addQty = 1;
            String qtyStr = req.getParameter("quantity");
            if (qtyStr != null && !qtyStr.trim().isEmpty()) {
                addQty = Math.max(1, Integer.parseInt(qtyStr.trim()));
            }

            Book_24110198 book = bookService.getBookById(bookId);
            if (book != null) {
                int maxStock = (book.getQuantity() != null) ? book.getQuantity() : 0;
                int currentQtyInCart = cart.containsKey(bookId) ? cart.get(bookId).getQuantity() : 0;
                int newQty = currentQtyInCart + addQty;

                // Giới hạn không vượt quá kho
                if (newQty > maxStock) {
                    newQty = maxStock;
                }

                if (newQty > 0) {
                    cart.put(bookId, CartItem_24110198.builder().book(book).quantity(newQty).build());
                }
            }
            resp.sendRedirect(req.getContextPath() + "/cart");

        } else if ("/cart-update".equals(path)) {
            int bookId = Integer.parseInt(req.getParameter("bookid"));
            int reqQty = Integer.parseInt(req.getParameter("quantity"));

            if (cart.containsKey(bookId)) {
                Book_24110198 book = cart.get(bookId).getBook();
                int maxStock = (book.getQuantity() != null) ? book.getQuantity() : 0;

                // Ràng buộc số lượng: tối thiểu 1, tối đa bằng maxStock
                if (reqQty <= 0) {
                    cart.remove(bookId);
                } else {
                    int finalQty = Math.min(reqQty, maxStock);
                    cart.get(bookId).setQuantity(finalQty);
                }
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}