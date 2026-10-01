<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Giỏ Hàng Của Bạn</title>
    <style>
        .cart-wrapper { max-width: 950px; margin: 30px auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 1px 4px rgba(0,0,0,0.1); }
        .cart-table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        .cart-table th, .cart-table td { border: 1px solid #dee2e6; padding: 12px; text-align: center; vertical-align: middle; }
        .cart-table th { background-color: #f8f9fa; }
        .qty-input { width: 60px; padding: 5px; text-align: center; border: 1px solid #ced4da; border-radius: 4px; }
        .btn-update { background: #17a2b8; color: #fff; border: none; padding: 6px 10px; border-radius: 4px; cursor: pointer; }
        .btn-del { color: #dc3545; text-decoration: none; font-weight: bold; }
        .cart-summary { display: flex; justify-content: space-between; align-items: center; margin-top: 25px; padding-top: 15px; border-top: 2px solid #eee; }
        .btn-checkout { background: #28a745; color: #fff; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold; font-size: 16px; }
        .btn-checkout:hover { background: #218838; }
        .btn-continue { background: #6c757d; color: #fff; padding: 10px 18px; text-decoration: none; border-radius: 4px; }
    </style>
</head>
<body>

<div class="cart-wrapper">
    <h2>GIỎ HÀNG</h2>

    <c:choose>
        <c:when test="${not empty sessionScope.cart && sessionScope.cart.size() > 0}">
            <table class="cart-table">
                <thead>
                    <tr>
                        <th style="width: 80px;">Hình ảnh</th>
                        <th>Tên sách</th>
                        <th style="width: 120px;">Đơn giá</th>
                        <th style="width: 170px;">Số lượng</th>
                        <th style="width: 130px;">Thành tiền</th>
                        <th style="width: 80px;">Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${sessionScope.cart}">
                        <c:set var="item" value="${entry.value}" />
                        <tr>
                            <td>
                                <img src="${pageContext.request.contextPath}/assets/images/${item.book.coverImage}" 
                                     alt="cover" style="width: 50px; height: 70px; object-fit: cover;" 
                                     onerror="this.src='https://via.placeholder.com/50x70?text=No+Img';" />
                            </td>
                            <td style="text-align: left;">
                                <strong>${item.book.title}</strong><br/>
                                <small style="color: #666;">Kho còn: ${item.book.quantity}</small>
                            </td>
                            <td>${item.book.price} đ</td>
                            <td>
                                <!-- Form cập nhật số lượng có ràng buộc min=1, max=tồn kho -->
                                <form action="${pageContext.request.contextPath}/cart-update" method="POST" style="display: flex; justify-content: center; gap: 6px;">
                                    <input type="hidden" name="bookid" value="${item.book.bookid}" />
                                    <input type="number" name="quantity" class="qty-input" value="${item.quantity}" min="1" max="${item.book.quantity}" required />
                                    <button type="submit" class="btn-update">Sửa</button>
                                </form>
                            </td>
                            <td><strong>${item.totalPrice} đ</strong></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/cart-delete?id=${item.book.bookid}" 
                                   class="btn-del" onclick="return confirm('Xóa sách này khỏi giỏ hàng?');">Xóa</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="cart-summary">
                <a href="${pageContext.request.contextPath}/home" class="btn-continue">← Tiếp tục mua sắm</a>
                <div>
                    <span style="font-size: 18px; margin-right: 20px;">Tổng thanh toán: <strong style="color: #d9534f; font-size: 22px;">${grandTotal} đ</strong></span>
                    <a href="${pageContext.request.contextPath}/checkout" class="btn-checkout">Tiến hành thanh toán COD</a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div style="text-align: center; padding: 40px 0;">
                <p style="font-size: 18px; color: #777;">Giỏ hàng của bạn đang trống!</p>
                <a href="${pageContext.request.contextPath}/home" class="btn-continue">Quay lại Cửa Hàng mua sách</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>