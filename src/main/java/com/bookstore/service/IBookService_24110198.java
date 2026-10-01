package com.bookstore.service;

import com.bookstore.model.Author_24110198;
import com.bookstore.model.Book_24110198;
import com.bookstore.model.Rating_24110198;

import java.util.List;

public interface IBookService_24110198 {
    List<Author_24110198> getAllAuthors();
    Author_24110198 getAuthorById(int authorId);
    List<Book_24110198> getBooksByAuthor(int authorId, int page, int pageSize);
    int countBooksByAuthor(int authorId);
    Book_24110198 getBookDetail(int bookId);
    List<Rating_24110198> getRatingsByBookId(int bookId);
    boolean addReview(Rating_24110198 rating);
    List<Book_24110198> getBooksPaginated(int page, int pageSize);
    int countAllBooks();
    Book_24110198 getBookById(int bookId);
    boolean createBook(Book_24110198 book, int authorId);
    boolean updateBook(Book_24110198 book, int authorId);
    boolean deleteBook(int bookId);
}