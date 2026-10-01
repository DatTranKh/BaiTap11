package com.bookstore.service.impl;

import com.bookstore.dao.AuthorDAO_24110198;
import com.bookstore.dao.BookDAO_24110198;
import com.bookstore.dao.RatingDAO_24110198;
import com.bookstore.model.Author_24110198;
import com.bookstore.model.Book_24110198;
import com.bookstore.model.Rating_24110198;
import com.bookstore.service.IBookService_24110198;

import java.util.List;

public class BookServiceImpl_24110198 implements IBookService_24110198 {
    private final BookDAO_24110198 bookDAO = new BookDAO_24110198();
    private final AuthorDAO_24110198 authorDAO = new AuthorDAO_24110198();
    private final RatingDAO_24110198 ratingDAO = new RatingDAO_24110198();

    @Override
    public List<Author_24110198> getAllAuthors() {
        return authorDAO.getAllAuthors();
    }

    @Override
    public Author_24110198 getAuthorById(int authorId) {
        return authorDAO.getAuthorById(authorId);
    }

    @Override
    public List<Book_24110198> getBooksByAuthor(int authorId, int page, int pageSize) {
        return bookDAO.getBooksByAuthor(authorId, page, pageSize);
    }

    @Override
    public int countBooksByAuthor(int authorId) {
        return bookDAO.countBooksByAuthor(authorId);
    }

    @Override
    public Book_24110198 getBookDetail(int bookId) {
        return bookDAO.getBookDetail(bookId);
    }

    @Override
    public List<Rating_24110198> getRatingsByBookId(int bookId) {
        return ratingDAO.getRatingsByBookId(bookId);
    }

    @Override
    public boolean addReview(Rating_24110198 rating) {
        return ratingDAO.insertOrUpdateRating(rating);
    }

    @Override
    public List<Book_24110198> getBooksPaginated(int page, int pageSize) {
        return bookDAO.getBooksPaginated(page, pageSize);
    }

    @Override
    public int countAllBooks() {
        return bookDAO.countAllBooks();
    }

    @Override
    public Book_24110198 getBookById(int bookId) {
        return bookDAO.getBookById(bookId);
    }

    @Override
    public boolean createBook(Book_24110198 book, int authorId) {
        return bookDAO.insertBook(book, authorId);
    }

    @Override
    public boolean updateBook(Book_24110198 book, int authorId) {
        return bookDAO.updateBook(book, authorId);
    }

    @Override
    public boolean deleteBook(int bookId) {
        return bookDAO.deleteBook(bookId);
    }
}