<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Xác Thực Mã OTP</title>
    <style>
        .otp-box { max-width: 400px; margin: 40px auto; padding: 25px; border: 1px solid #ddd; border-radius: 8px; text-align: center; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
        .otp-box h2 { color: #2c3e50; }
        .form-group input { width: 80%; padding: 12px; font-size: 20px; text-align: center; letter-spacing: 5px; border: 1px solid #aaa; border-radius: 4px; margin: 15px 0; }
        .btn-verify { width: 85%; padding: 12px; background-color: #2980b9; color: white; border: none; border-radius: 4px; font-size: 16px; font-weight: bold; cursor: pointer; }
        .btn-verify:hover { background-color: #2471a3; }
        .alert-error { color: #721c24; background-color: #f8d7da; border: 1px solid #f5c6cb; padding: 10px; border-radius: 4px; margin-bottom: 15px; }
    </style>
</head>
<body>
    <div class="otp-box">
        <h2>KÍCH HOẠT TÀI KHOẢN</h2>
        <p>Mã xác thực gồm 6 chữ số đã được gửi tới email: <strong>${sessionScope.pendingUser.email}</strong>[cite: 1]</p>

        <c:if test="${not empty errorMessage}">
            <div class="alert-error">${errorMessage}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/verify-otp" method="POST">
            <div class="form-group">
                <input type="text" name="otp" maxlength="6" pattern="[0-9]{6}" required placeholder="123456" autofocus />
            </div>
            <button type="submit" class="btn-verify">Kích Hoạt Tài Khoản</button>
        </form>
    </div>
</body>
</html>