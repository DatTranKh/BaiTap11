<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đặt Hàng Thành Công</title>
    <style>
        .success-box { max-width: 550px; margin: 40px auto; background: #fff; padding: 35px; border-radius: 8px; text-align: center; box-shadow: 0 1px 4px rgba(0,0,0,0.1); }
        .icon-success { font-size: 50px; color: #28a745; margin-bottom: 10px; }
        .btn-home { display: inline-block; background: #007bff; color: #fff; padding: 10px 20px; border-radius: 4px; text-decoration: none; margin-top: 20px; }
    </style>
</head>
<body>

<div class="success-box">
    <div class="icon-success">✓</div>
    <h2 style="color: #28a745; margin-top: 0;">ĐẶT HÀNG THÀNH CÔNG!</h2>
    <p>Mã số đơn hàng của bạn: <strong>#${param.orderId}</strong></p>
    <p>Phương thức thanh toán: <strong>Thanh toán tiền mặt khi nhận hàng (COD)</strong>.</p>
    <p style="color: #666; font-size: 14px;">Chúng tôi đã ghi nhận đơn hàng và sẽ liên hệ sớm nhất để tiến hành giao hàng cho bạn.</p>
    <a href="${pageContext.request.contextPath}/home" class="btn-home">Về Trang Chủ Tiếp Tục Mua Sắm</a>
</div>

</body>
</html>