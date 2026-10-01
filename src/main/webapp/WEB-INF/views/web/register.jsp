<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng Ký Tài Khoản</title>
    <style>
        .auth-box { max-width: 420px; margin: 30px auto; padding: 25px; border: 1px solid #ddd; border-radius: 8px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
        .auth-box h2 { text-align: center; margin-bottom: 20px; color: #2c3e50; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 6px; font-weight: bold; }
        .form-group input { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .btn-submit { width: 100%; padding: 12px; background-color: #27ae60; color: white; border: none; border-radius: 4px; font-size: 16px; font-weight: bold; cursor: pointer; }
        .btn-submit:hover { background-color: #219150; }
        .alert-error { color: #721c24; background-color: #f8d7da; border: 1px solid #f5c6cb; padding: 10px; border-radius: 4px; margin-bottom: 15px; }
        .auth-link { text-align: center; margin-top: 15px; font-size: 14px; }
    </style>
</head>
<body>
    <div class="auth-box">
        <h2>ĐĂNG KÝ TÀI KHOẢN</h2>

        <c:if test="${not empty errorMessage}">
            <div class="alert-error">${errorMessage}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="POST">
            <div class="form-group">
                <label for="email">Địa chỉ Email:</label>
                <input type="email" id="email" name="email" value="${email}" required placeholder="example@gmail.com" />
            </div>

            <div class="form-group">
                <label for="fullname">Họ và tên:</label>
                <input type="text" id="fullname" name="fullname" value="${fullname}" required placeholder="Nguyễn Văn A" />
            </div>

            <div class="form-group">
                <label for="phone">Số điện thoại:</label>
                <input type="number" id="phone" name="phone" value="${phone}" placeholder="0901234567" />
            </div>

            <div class="form-group">
                <label for="passwd">Mật khẩu:</label>
                <input type="password" id="passwd" name="passwd" required placeholder="Tối đa 32 ký tự" maxlength="32" />
            </div>

            <button type="submit" class="btn-submit">Nhận mã OTP & Đăng Ký</button>
        </form>

        <div class="auth-link">
            Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập ngay</a>
        </div>
    </div>
</body>
</html>