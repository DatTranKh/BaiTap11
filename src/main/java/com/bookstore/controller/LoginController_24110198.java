package com.bookstore.controller;

import com.bookstore.model.User_24110198;
import com.bookstore.service.IUserService_24110198;
import com.bookstore.service.impl.UserServiceImpl_24110198;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginController_24110198 extends HttpServlet {
    private final IUserService_24110198 userService = new UserServiceImpl_24110198();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String success = req.getParameter("success");
        if ("registered".equals(success)) {
            req.setAttribute("successMessage", "Kích hoạt tài khoản thành công! Vui lòng đăng nhập.");
        }
        req.getRequestDispatcher("/WEB-INF/views/web/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String passwd = req.getParameter("passwd");

        User_24110198 user = userService.login(email, passwd);

        // Đăng nhập thành công và vai trò là User (is_admin == false)[cite: 1]
        if (user != null && !user.isAdmin()) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        // Đăng nhập với vai trò Admin (hỗ trợ thêm quản trị viên vào trang admin)[cite: 1]
        if (user != null && user.isAdmin()) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/admin/books");
            return;
        }

        // Ngược lại (sai tài khoản/mật khẩu hoặc đăng nhập thất bại) quay lại trang đăng nhập[cite: 1]
        req.setAttribute("errorMessage", "Email hoặc mật khẩu không chính xác!");
        req.setAttribute("email", email);
        req.getRequestDispatcher("/WEB-INF/views/web/login.jsp").forward(req, resp);
    }
}