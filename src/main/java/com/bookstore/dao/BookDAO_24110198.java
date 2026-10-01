package com.bookstore.dao;

import com.bookstore.connection.DBContext_24110198;
import com.bookstore.model.Author_24110198;
import com.bookstore.model.Book_24110198;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO_24110198 {

    // Phân trang 3 sản phẩm/trang theo từng tác giả
    public List<Book_24110198> getBooksByAuthor(int authorId, int page, int pageSize) {
        List<Book_24110198> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        
        String sql = "SELECT b.*, " +
                     "       (SELECT COUNT(*) FROM dbo.rating r WHERE r.bookid = b.bookid) AS review_count " +
                     "FROM dbo.books b " +
                     "JOIN dbo.book_author ba ON b.bookid = ba.bookid " +
                     "WHERE ba.author_id = ? " +
                     "ORDER BY b.bookid ASC " +
                     "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, authorId);
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book_24110198 b = Book_24110198.builder()
                            .bookid(rs.getInt("bookid"))
                            .isbn(rs.getObject("isbn") != null ? rs.getInt("isbn") : null)
                            .title(rs.getString("title"))
                            .publisher(rs.getString("publisher"))
                            .price(rs.getBigDecimal("price"))
                            .description(rs.getString("description"))
                            .publishDate(rs.getDate("publish_date"))
                            .coverImage(rs.getString("cover_image"))
                            .quantity(rs.getObject("quantity") != null ? rs.getInt("quantity") : null)
                            .reviewCount(rs.getInt("review_count"))
                            .build();

                    b.setAuthors(getAuthorsByBookId(b.getBookid()));
                    list.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Đếm tổng số sách của một tác giả để tính tổng số trang
    public int countBooksByAuthor(int authorId) {
        String sql = "SELECT COUNT(*) FROM dbo.book_author WHERE author_id = ?";
        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, authorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy danh sách tác giả của một cuốn sách
    public List<Author_24110198> getAuthorsByBookId(int bookId) {
        List<Author_24110198> authors = new ArrayList<>();
        String sql = "SELECT a.* FROM dbo.author a " +
                     "JOIN dbo.book_author ba ON a.author_id = ba.author_id " +
                     "WHERE ba.bookid = ?";
        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    authors.add(Author_24110198.builder()
                            .authorId(rs.getInt("author_id"))
                            .authorName(rs.getString("author_name"))
                            .dateOfBirth(rs.getDate("date_of_birth"))
                            .build());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }
    
    public Book_24110198 getBookDetail(int bookId) {
        String sql = "SELECT * FROM dbo.books WHERE bookid = ?";
        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Book_24110198 book = Book_24110198.builder()
                            .bookid(rs.getInt("bookid"))
                            .isbn(rs.getObject("isbn") != null ? rs.getInt("isbn") : null)
                            .title(rs.getString("title"))
                            .publisher(rs.getString("publisher"))
                            .price(rs.getBigDecimal("price"))
                            .description(rs.getString("description"))
                            .publishDate(rs.getDate("publish_date"))
                            .coverImage(rs.getString("cover_image"))
                            .quantity(rs.getObject("quantity") != null ? rs.getInt("quantity") : null)
                            .build();

                    book.setAuthors(getAuthorsByBookId(bookId));
                    book.setReviewCount(new RatingDAO_24110198().countRatingsByBookId(bookId));
                    return book;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
 // 1. READ (Xem danh sách có phân trang)
    public List<Book_24110198> getBooksPaginated(int page, int pageSize) {
        List<Book_24110198> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM dbo.books ORDER BY bookid DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book_24110198 b = extractBook(rs);
                    b.setAuthors(getAuthorsByBookId(b.getBookid()));
                    list.add(b);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Đếm tổng số cuốn sách để tính số trang
    public int countAllBooks() {
        String sql = "SELECT COUNT(*) FROM dbo.books";
        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 2. READ (Xem chi tiết 1 cuốn sách)
    public Book_24110198 getBookById(int bookId) {
        String sql = "SELECT * FROM dbo.books WHERE bookid = ?";
        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Book_24110198 b = extractBook(rs);
                    b.setAuthors(getAuthorsByBookId(bookId));
                    return b;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3. CREATE (Thêm mới sách và liên kết bảng book_author)
    public boolean insertBook(Book_24110198 book, int authorId) {
        String sqlBook = "INSERT INTO dbo.books (isbn, title, publisher, price, description, publish_date, cover_image, quantity) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlRel = "INSERT INTO dbo.book_author (bookid, author_id) VALUES (?, ?)";

        Connection conn = null;
        try {
            conn = DBContext_24110198.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            int generatedBookId = 0;
            try (PreparedStatement psBook = conn.prepareStatement(sqlBook, Statement.RETURN_GENERATED_KEYS)) {
                if (book.getIsbn() != null) psBook.setInt(1, book.getIsbn()); else psBook.setNull(1, Types.INTEGER);
                psBook.setString(2, book.getTitle());
                psBook.setString(3, book.getPublisher());
                psBook.setBigDecimal(4, book.getPrice());
                psBook.setString(5, book.getDescription());
                psBook.setDate(6, book.getPublishDate());
                psBook.setString(7, book.getCoverImage());
                if (book.getQuantity() != null) psBook.setInt(8, book.getQuantity()); else psBook.setNull(8, Types.INTEGER);

                psBook.executeUpdate();
                try (ResultSet rs = psBook.getGeneratedKeys()) {
                    if (rs.next()) generatedBookId = rs.getInt(1);
                }
            }

            if (generatedBookId > 0 && authorId > 0) {
                try (PreparedStatement psRel = conn.prepareStatement(sqlRel)) {
                    psRel.setInt(1, generatedBookId);
                    psRel.setInt(2, authorId);
                    psRel.executeUpdate();
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
        return false;
    }

    // 4. UPDATE (Cập nhật sách và cập nhật tác giả liên kết)
    public boolean updateBook(Book_24110198 book, int authorId) {
        String sqlBook = "UPDATE dbo.books SET isbn = ?, title = ?, publisher = ?, price = ?, " +
                         "description = ?, publish_date = ?, cover_image = ?, quantity = ? WHERE bookid = ?";
        String sqlDelRel = "DELETE FROM dbo.book_author WHERE bookid = ?";
        String sqlInsRel = "INSERT INTO dbo.book_author (bookid, author_id) VALUES (?, ?)";

        Connection conn = null;
        try {
            conn = DBContext_24110198.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlBook)) {
                if (book.getIsbn() != null) ps.setInt(1, book.getIsbn()); else ps.setNull(1, Types.INTEGER);
                ps.setString(2, book.getTitle());
                ps.setString(3, book.getPublisher());
                ps.setBigDecimal(4, book.getPrice());
                ps.setString(5, book.getDescription());
                ps.setDate(6, book.getPublishDate());
                ps.setString(7, book.getCoverImage());
                if (book.getQuantity() != null) ps.setInt(8, book.getQuantity()); else ps.setNull(8, Types.INTEGER);
                ps.setInt(9, book.getBookid());
                ps.executeUpdate();
            }

            if (authorId > 0) {
                try (PreparedStatement psDel = conn.prepareStatement(sqlDelRel)) {
                    psDel.setInt(1, book.getBookid());
                    psDel.executeUpdate();
                }
                try (PreparedStatement psIns = conn.prepareStatement(sqlInsRel)) {
                    psIns.setInt(1, book.getBookid());
                    psIns.setInt(2, authorId);
                    psIns.executeUpdate();
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
        return false;
    }

    // 5. DELETE (Xóa sách)
    public boolean deleteBook(int bookId) {
        String sql = "DELETE FROM dbo.books WHERE bookid = ?";
        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Book_24110198 extractBook(ResultSet rs) throws SQLException {
        return Book_24110198.builder()
                .bookid(rs.getInt("bookid"))
                .isbn(rs.getObject("isbn") != null ? rs.getInt("isbn") : null)
                .title(rs.getString("title"))
                .publisher(rs.getString("publisher"))
                .price(rs.getBigDecimal("price"))
                .description(rs.getString("description"))
                .publishDate(rs.getDate("publish_date"))
                .coverImage(rs.getString("cover_image"))
                .quantity(rs.getObject("quantity") != null ? rs.getInt("quantity") : null)
                .build();
    }
}