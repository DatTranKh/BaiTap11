package com.bookstore.dao;

import com.bookstore.connection.DBContext_24110198;
import com.bookstore.model.User_24110198;
import java.sql.*;

public class UserDAO_24110198 {

    public User_24110198 checkLogin(String email, String password) {
        String sql = "SELECT * FROM dbo.users WHERE email = ? AND passwd = ?";
        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User_24110198 getUserByEmail(String email) {
        String sql = "SELECT * FROM dbo.users WHERE email = ?";
        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertUser(User_24110198 user) {
        String sql = "INSERT INTO dbo.users (email, fullname, phone, passwd, signup_date, is_admin) " +
                     "VALUES (?, ?, ?, ?, GETDATE(), ?)";
        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFullname());
            if (user.getPhone() != null) {
                ps.setInt(3, user.getPhone());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, user.getPasswd());
            ps.setBoolean(5, user.isAdmin());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateLastLogin(int userId) {
        String sql = "UPDATE dbo.users SET last_login = GETDATE() WHERE id = ?";
        try (Connection conn = DBContext_24110198.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User_24110198 extractUser(ResultSet rs) throws SQLException {
        return User_24110198.builder()
                .id(rs.getInt("id"))
                .email(rs.getString("email"))
                .fullname(rs.getString("fullname"))
                .phone(rs.getObject("phone") != null ? rs.getInt("phone") : null)
                .passwd(rs.getString("passwd"))
                .signupDate(rs.getTimestamp("signup_date"))
                .lastLogin(rs.getTimestamp("last_login"))
                .isAdmin(rs.getBoolean("is_admin"))
                .build();
    }
}