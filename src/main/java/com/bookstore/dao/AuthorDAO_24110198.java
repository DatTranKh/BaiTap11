package com.bookstore.dao;

import com.bookstore.connection.DBContext_24110198;
import com.bookstore.model.Author_24110198;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO_24110198 {

    public List<Author_24110198> getAllAuthors() {
        List<Author_24110198> list = new ArrayList<>();
        String sql = "SELECT * FROM dbo.author ORDER BY author_name";
        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(Author_24110198.builder()
                        .authorId(rs.getInt("author_id"))
                        .authorName(rs.getString("author_name"))
                        .dateOfBirth(rs.getDate("date_of_birth"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Author_24110198 getAuthorById(int authorId) {
        String sql = "SELECT * FROM dbo.author WHERE author_id = ?";
        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, authorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Author_24110198.builder()
                            .authorId(rs.getInt("author_id"))
                            .authorName(rs.getString("author_name"))
                            .dateOfBirth(rs.getDate("date_of_birth"))
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}