package com.bookstore.controller;

import com.bookstore.model.User_24110198;
import com.bookstore.service.IUserService_24110198;
import com.bookstore.service.impl.UserServiceImpl_24110198;
import com.bookstore.util.EmailUtil_24110198;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(urlPatterns = {"/register", "/verify-otp"})
public class RegisterController_24110198 extends HttpServlet {
    private final IUserService_24110198 userService = new UserServiceImpl_24110198();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/verify-otp".equals(path)) {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("pendingUser") == null) {
                resp.sendRedirect(req.getContextPath() + "/register");
                return;
            }
            req.getRequestDispatcher("/WEB-INF/views/web/verify_otp.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/web/register.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        if ("/register".equals(path)) {
            String email = req.getParameter("email");
            String fullname = req.getParameter("fullname");
            String phoneStr = req.getParameter("phone");
            String passwd = req.getParameter("passwd");

            // Kiểm tra email đã tồn tại hay chưa
            if (userService.findByEmail(email) != null) {
                req.setAttribute("errorMessage", "Email này đã được đăng ký trong hệ thống!");
                req.setAttribute("email", email);
                req.setAttribute("fullname", fullname);
                req.setAttribute("phone", phoneStr);
                req.getRequestDispatcher("/WEB-INF/views/web/register.jsp").forward(req, resp);
                return;
            }

            Integer phone = null;
            if (phoneStr != null && !phoneStr.trim().isEmpty()) {
                try {
                    phone = Integer.parseInt(phoneStr.trim());
                } catch (NumberFormatException ignored) {}
            }

            User_24110198 pendingUser = User_24110198.builder()
                    .email(email)
                    .fullname(fullname)
                    .phone(phone)
                    .passwd(passwd)
                    .isAdmin(false)
                    .build();

            // Sinh mã OTP và gửi qua Email
            String otp = EmailUtil_24110198.generateOTP();
            EmailUtil_24110198.sendOtpEmail(email, otp);

            // Lưu tạm vào Session phục vụ kích hoạt
            HttpSession session = req.getSession();
            session.setAttribute("pendingUser", pendingUser);
            session.setAttribute("registeredOtp", otp);

            resp.sendRedirect(req.getContextPath() + "/verify-otp");

        } else if ("/verify-otp".equals(path)) {
            String userEnteredOtp = req.getParameter("otp");
            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("registeredOtp") == null) {
                resp.sendRedirect(req.getContextPath() + "/register");
                return;
            }

            String serverOtp = (String) session.getAttribute("registeredOtp");
            User_24110198 pendingUser = (User_24110198) session.getAttribute("pendingUser");

            if (serverOtp != null && serverOtp.equals(userEnteredOtp != null ? userEnteredOtp.trim() : "")) {
                userService.register(pendingUser);

                // Dọn dẹp session sau khi kích hoạt thành công
                session.removeAttribute("registeredOtp");
                session.removeAttribute("pendingUser");

                resp.sendRedirect(req.getContextPath() + "/login?success=registered");
            } else {
                req.setAttribute("errorMessage", "Mã OTP không chính xác. Vui lòng kiểm tra lại!");
                req.getRequestDispatcher("/WEB-INF/views/web/verify_otp.jsp").forward(req, resp);
            }
        }
    }
}