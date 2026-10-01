<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thanh Toán Đơn Hàng (COD)</title>
    <style>
        .checkout-container { max-width: 900px; margin: 30px auto; background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 1px 4px rgba(0,0,0,0.1); }
        .checkout-grid { display: flex; gap: 30px; }
        .form-col { flex: 1.2; }
        .summary-col { flex: 0.8; background: #f8f9fa; padding: 20px; border-radius: 6px; border: 1px solid #dee2e6; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; font-weight: bold; margin-bottom: 5px; }
        .form-group input, .form-group textarea { width: 100%; padding: 9px 12px; border: 1px solid #ced4da; border-radius: 4px; box-sizing: border-box; }
        .btn-confirm-order { width: 100%; padding: 13px; background-color: #e67e22; color: #fff; font-size: 16px; font-weight: bold; border: none; border-radius: 4px; cursor: pointer; margin-top: 15px; }
        .btn-confirm-order:hover { background-color: #d35400; }
        .alert-danger { background: #f8d7da; color: #721c24; padding: 10px; border-radius: 4px; margin-bottom: 15px; }
        .payment-box { background: #e8f4fd; border: 1px solid #bee5eb; padding: 12px; border-radius: 4px; margin-top: 10px; }
    </style>
</head>
<body>

<div class="checkout-container">
    <h2 style="margin-top: 0; color: #2c3e50;">THANH TOÁN ĐƠN HÀNG</h2>

    <c:if test="${not empty errorMessage}">
        <div class="alert-danger">${errorMessage}</div>
    </c:if>

    <div class="checkout-grid">
        <!-- Cột thông tin nhận hàng -->
        <div class="form-col">
            <h4 style="margin-top: 0;">Thông tin người nhận</h4>
            <form action="${pageContext.request.contextPath}/checkout" method="POST">
                <div class="form-group">
                    <label for="customerName">Họ và tên người nhận (*):</label>
                    <input type="text" id="customerName" name="customerName" value="${sessionScope.user != null ? sessionScope.user.fullname : ''}" required />
                </div>

                <div class="form-group">
                    <label for="customerPhone">Số điện thoại liên lạc (*):</label>
                    <input type="tel" id="customerPhone" name="customerPhone" value="${sessionScope.user != null ? sessionScope.user.phone : ''}" required />
                </div>

                <div class="form-group">
                    <label for="shippingAddress">Địa chỉ nhận hàng chi tiết (*):</label>
                    <input type="text" id="shippingAddress" name="shippingAddress" placeholder="Số nhà, tên đường, phường/xã, quận/huyện..." required />
                </div>

                <div class="form-group">
                    <label for="note">Ghi chú giao hàng:</label>
                    <textarea id="note" name="note" rows="3" placeholder="Ví dụ: Giao giờ hành chính, gọi trước khi đến..."></textarea>
                </div>

                <div class="payment-box">
                    <label style="font-weight: bold; display: flex; align-items: center; gap: 8px;">
                        <input type="radio" name="paymentMethod" value="COD" checked />
                        Thanh toán khi nhận hàng (COD)
                    </label>
                    <p style="margin: 5px 0 0 25px; font-size: 13px; color: #555;">
                        Bạn sẽ thanh toán tiền mặt trực tiếp cho nhân viên giao hàng khi nhận được sách.
                    </p>
                </div>

                <button type="submit" class="btn-confirm-order">XÁC NHẬN ĐẶT HÀNG (COD)</button>
            </form>
        </div>

        <!-- Cột tóm tắt đơn hàng -->
        <div class="summary-col">
            <h4 style="margin-top: 0;">Đơn hàng của bạn</h4>
            <ul style="list-style: none; padding: 0; margin: 0 0 15px 0;">
                <c:forEach var="entry" items="${sessionScope.cart}">
                    <c:set var="item" value="${entry.value}" />
                    <li style="display: flex; justify-content: space-between; margin-bottom: 10px; font-size: 14px; border-bottom: 1px dashed #ccc; padding-bottom: 6px;">
                        <span>${item.book.title} <strong>x${item.quantity}</strong></span>
                        <span>${item.totalPrice} đ</span>
                    </li>
                </c:forEach>
            </ul>
            <div style="display: flex; justify-content: space-between; font-size: 16px; margin-top: 15px;">
                <strong>Tổng cộng:</strong>
                <strong style="color: #d9534f; font-size: 18px;">${grandTotal} đ</strong>
            </div>
        </div>
    </div>
</div>

</body>
</html>