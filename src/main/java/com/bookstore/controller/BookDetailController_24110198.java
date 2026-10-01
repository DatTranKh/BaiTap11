package com.bookstore.controller;

import com.bookstore.model.Book_24110198;
import com.bookstore.model.Rating_24110198;
import com.bookstore.model.User_24110198;
import com.bookstore.service.IBookService_24110198;
import com.bookstore.service.impl.BookServiceImpl_24110198;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/book-detail"})
public class BookDetailController_24110198 extends HttpServlet {
    private final IBookService_24110198 bookService = new BookServiceImpl_24110198();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        try {
            int bookId = Integer.parseInt(idParam.trim());
            Book_24110198 book = bookService.getBookDetail(bookId);

            if (book == null) {
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }

            List<Rating_24110198> ratings = bookService.getRatingsByBookId(bookId);
            req.setAttribute("book", book);
            req.setAttribute("ratings", ratings);

            req.getRequestDispatcher("/WEB-INF/views/web/detail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        User_24110198 user = (session != null) ? (User_24110198) session.getAttribute("user") : null;

        String bookIdParam = req.getParameter("bookid");
        int bookId = Integer.parseInt(bookIdParam);

        // Yêu cầu đăng nhập trước khi thêm reviews[cite: 1, 5]
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login?error=needLogin");
            return;
        }

        String reviewText = req.getParameter("review_text");
        String ratingParam = req.getParameter("rating");
        int ratingValue = 5;
        if (ratingParam != null && !ratingParam.trim().isEmpty()) {
            try {
                ratingValue = Integer.parseInt(ratingParam.trim());
            } catch (NumberFormatException ignored) {}
        }

        if (reviewText != null && !reviewText.trim().isEmpty()) {
            Rating_24110198 newRating = Rating_24110198.builder()
                    .userId(user.getId())
                    .bookId(bookId)
                    .rating(ratingValue)
                    .reviewText(reviewText.trim())
                    .build();

            bookService.addReview(newRating);
        }

        resp.sendRedirect(req.getContextPath() + "/book-detail?id=" + bookId);
    }
}