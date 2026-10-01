package com.bookstore.controller;

import com.bookstore.model.Author_24110198;
import com.bookstore.model.Book_24110198;
import com.bookstore.service.IBookService_24110198;
import com.bookstore.service.impl.BookServiceImpl_24110198;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/home", ""})
public class HomeController_24110198 extends HttpServlet {
    private final IBookService_24110198 bookService = new BookServiceImpl_24110198();
    private static final int PAGE_SIZE = 3; // 03sp/trang theo yêu cầu đề[cite: 1, 4]

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Author_24110198> authors = bookService.getAllAuthors();
        req.setAttribute("authors", authors);

        if (authors != null && !authors.isEmpty()) {
            int selectedAuthorId = authors.get(0).getAuthorId(); // Mặc định chọn tác giả đầu tiên
            String authorParam = req.getParameter("authorId");
            if (authorParam != null && !authorParam.trim().isEmpty()) {
                try {
                    selectedAuthorId = Integer.parseInt(authorParam.trim());
                } catch (NumberFormatException ignored) {}
            }

            int page = 1;
            String pageParam = req.getParameter("page");
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                try {
                    page = Math.max(1, Integer.parseInt(pageParam.trim()));
                } catch (NumberFormatException ignored) {}
            }

            int totalBooks = bookService.countBooksByAuthor(selectedAuthorId);
            int totalPages = (int) Math.ceil((double) totalBooks / PAGE_SIZE);
            if (totalPages == 0) totalPages = 1;

            if (page > totalPages) {
                page = totalPages;
            }

            List<Book_24110198> books = bookService.getBooksByAuthor(selectedAuthorId, page, PAGE_SIZE);
            Author_24110198 selectedAuthor = bookService.getAuthorById(selectedAuthorId);

            req.setAttribute("selectedAuthor", selectedAuthor);
            req.setAttribute("books", books);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
        }

        req.getRequestDispatcher("/WEB-INF/views/web/home.jsp").forward(req, resp);
    }
}