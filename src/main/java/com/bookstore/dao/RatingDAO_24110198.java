package com.bookstore.dao;

import com.bookstore.connection.DBContext_24110198;
import com.bookstore.model.Rating_24110198;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingDAO_24110198 {

    // Lấy danh sách reviews của 1 cuốn sách (kèm tên người dùng)
    public List<Rating_24110198> getRatingsByBookId(int bookId) {
        List<Rating_24110198> list = new ArrayList<>();
        String sql = "SELECT r.*, u.fullname FROM dbo.rating r " +
                     "JOIN dbo.users u ON r.userid = u.id " +
                     "WHERE r.bookid = ? ORDER BY r.userid DESC";
        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(Rating_24110198.builder()
                            .userId(rs.getInt("userid"))
                            .bookId(rs.getInt("bookid"))
                            .rating(rs.getObject("rating") != null ? rs.getInt("rating") : null)
                            .reviewText(rs.getString("review_text"))
                            .userFullname(rs.getString("fullname"))
                            .build());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Đếm tổng số review của sách
    public int countRatingsByBookId(int bookId) {
        String sql = "SELECT COUNT(*) FROM dbo.rating WHERE bookid = ?";
        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
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

    // Thêm mới hoặc cập nhật review của user cho cuốn sách[cite: 1, 5]
    public boolean insertOrUpdateRating(Rating_24110198 rating) {
        String checkSql = "SELECT COUNT(*) FROM dbo.rating WHERE userid = ? AND bookid = ?";
        String insertSql = "INSERT INTO dbo.rating (userid, bookid, rating, review_text) VALUES (?, ?, ?, ?)";
        String updateSql = "UPDATE dbo.rating SET rating = ?, review_text = ? WHERE userid = ? AND bookid = ?";

        try (Connection conn = DBContext_24110198.getConnection()) {
            boolean exists = false;
            try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setInt(1, rating.getUserId());
                psCheck.setInt(2, rating.getBookId());
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        exists = true;
                    }
                }
            }

            if (exists) {
                try (PreparedStatement psUp = conn.prepareStatement(updateSql)) {
                    psUp.setInt(1, rating.getRating() != null ? rating.getRating() : 5);
                    psUp.setString(2, rating.getReviewText());
                    psUp.setInt(3, rating.getUserId());
                    psUp.setInt(4, rating.getBookId());
                    return psUp.executeUpdate() > 0;
                }
            } else {
                try (PreparedStatement psIn = conn.prepareStatement(insertSql)) {
                    psIn.setInt(1, rating.getUserId());
                    psIn.setInt(2, rating.getBookId());
                    psIn.setInt(3, rating.getRating() != null ? rating.getRating() : 5);
                    psIn.setString(4, rating.getReviewText());
                    return psIn.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}