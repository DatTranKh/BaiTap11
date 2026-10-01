package com.bookstore.controller.admin;

import com.bookstore.model.Book_24110198;
import com.bookstore.service.IBookService_24110198;
import com.bookstore.service.impl.BookServiceImpl_24110198;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@WebServlet(urlPatterns = {
    "/admin/books",
    "/admin/book-add",
    "/admin/book-edit",
    "/admin/book-view",
    "/admin/book-delete"
})
public class AdminBookController_24110198 extends HttpServlet {
    private final IBookService_24110198 bookService = new BookServiceImpl_24110198();
    private static final int PAGE_SIZE = 5; // 5 sách mỗi trang trên bảng admin

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        switch (path) {
            case "/admin/book-add": {
                req.setAttribute("authors", bookService.getAllAuthors());
                req.setAttribute("isEdit", false);
                req.getRequestDispatcher("/WEB-INF/views/admin/book_form.jsp").forward(req, resp);
                break;
            }
            case "/admin/book-edit": {
                int id = Integer.parseInt(req.getParameter("id"));
                Book_24110198 book = bookService.getBookById(id);
                req.setAttribute("book", book);
                req.setAttribute("authors", bookService.getAllAuthors());
                req.setAttribute("isEdit", true);
                req.getRequestDispatcher("/WEB-INF/views/admin/book_form.jsp").forward(req, resp);
                break;
            }
            case "/admin/book-view": {
                int id = Integer.parseInt(req.getParameter("id"));
                Book_24110198 book = bookService.getBookById(id);
                req.setAttribute("book", book);
                req.getRequestDispatcher("/WEB-INF/views/admin/book_view.jsp").forward(req, resp);
                break;
            }
            case "/admin/book-delete": {
                int id = Integer.parseInt(req.getParameter("id"));
                bookService.deleteBook(id);
                resp.sendRedirect(req.getContextPath() + "/admin/books?msg=deleteSuccess");
                break;
            }
            default: { // /admin/books (Danh sách + Phân trang)[cite: 1]
                int page = 1;
                String pageParam = req.getParameter("page");
                if (pageParam != null && !pageParam.trim().isEmpty()) {
                    try {
                        page = Math.max(1, Integer.parseInt(pageParam.trim()));
                    } catch (NumberFormatException ignored) {}
                }

                int totalBooks = bookService.countAllBooks();
                int totalPages = (int) Math.ceil((double) totalBooks / PAGE_SIZE);
                if (totalPages == 0) totalPages = 1;
                if (page > totalPages) page = totalPages;

                List<Book_24110198> list = bookService.getBooksPaginated(page, PAGE_SIZE);

                req.setAttribute("books", list);
                req.setAttribute("currentPage", page);
                req.setAttribute("totalPages", totalPages);
                req.setAttribute("totalBooks", totalBooks);
                req.getRequestDispatcher("/WEB-INF/views/admin/book_list.jsp").forward(req, resp);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        String bookidStr = req.getParameter("bookid");
        String isbnStr = req.getParameter("isbn");
        String title = req.getParameter("title");
        String publisher = req.getParameter("publisher");
        String priceStr = req.getParameter("price");
        String description = req.getParameter("description");
        String publishDateStr = req.getParameter("publish_date");
        String coverImage = req.getParameter("cover_image");
        String quantityStr = req.getParameter("quantity");
        String authorIdStr = req.getParameter("author_id");

        Integer isbn = (isbnStr != null && !isbnStr.trim().isEmpty()) ? Integer.parseInt(isbnStr.trim()) : null;
        BigDecimal price = (priceStr != null && !priceStr.trim().isEmpty()) ? new BigDecimal(priceStr.trim()) : BigDecimal.ZERO;
        Date publishDate = (publishDateStr != null && !publishDateStr.trim().isEmpty()) ? Date.valueOf(publishDateStr.trim()) : null;
        Integer quantity = (quantityStr != null && !quantityStr.trim().isEmpty()) ? Integer.parseInt(quantityStr.trim()) : 0;
        int authorId = (authorIdStr != null && !authorIdStr.trim().isEmpty()) ? Integer.parseInt(authorIdStr.trim()) : 0;

        Book_24110198 book = Book_24110198.builder()
                .isbn(isbn)
                .title(title)
                .publisher(publisher)
                .price(price)
                .description(description)
                .publishDate(publishDate)
                .coverImage(coverImage)
                .quantity(quantity)
                .build();

        if ("/admin/book-add".equals(path)) {
            bookService.createBook(book, authorId);
        } else if ("/admin/book-edit".equals(path)) {
            book.setBookid(Integer.parseInt(bookidStr));
            bookService.updateBook(book, authorId);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/books?msg=success");
    }
}