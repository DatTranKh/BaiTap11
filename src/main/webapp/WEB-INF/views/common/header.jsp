<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<nav
	style="background-color: #2c3e50; padding: 14px 25px; color: #fff; display: flex; justify-content: space-between; align-items: center; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);">
	<div style="display: flex; gap: 20px; align-items: center;">
		<a href="${pageContext.request.contextPath}/home"
			style="color: #fff; font-weight: bold; text-decoration: none; font-size: 16px;">Trang
			Chủ</a> <a href="${pageContext.request.contextPath}/home"
			style="color: #ecf0f1; text-decoration: none;">Sản phẩm</a> <a
			href="${pageContext.request.contextPath}/cart"
			style="color: #ecf0f1; text-decoration: none; position: relative;">
			🛒 Giỏ hàng <c:if
				test="${not empty sessionScope.cart && sessionScope.cart.size() > 0}">
				<span
					style="background: #e74c3c; color: white; border-radius: 50%; padding: 2px 6px; font-size: 11px;">
					${sessionScope.cart.size()} </span>
			</c:if>
		</a>

		<%-- Chỉ hiển thị 'Trang quản trị' khi vai trò là Admin[cite: 1] --%>
		<c:if test="${sessionScope.user != null && sessionScope.user.admin}">
			<a href="${pageContext.request.contextPath}/admin/books"
				style="background-color: #e67e22; color: #fff; padding: 5px 12px; border-radius: 4px; text-decoration: none; font-weight: bold;">Trang
				quản trị</a>
		</c:if>

	</div>

	<div>
		<c:choose>
			<c:when test="${sessionScope.user == null}">
				<a href="${pageContext.request.contextPath}/login"
					style="background-color: #27ae60; color: #fff; padding: 6px 15px; border-radius: 4px; text-decoration: none;">Đăng
					nhập</a>
			</c:when>
			<c:otherwise>
				<span style="margin-right: 15px;">Xin chào, <strong>${sessionScope.user.fullname}</strong></span>
				<a href="${pageContext.request.contextPath}/logout"
					style="color: #e74c3c; text-decoration: none;">Đăng xuất</a>
			</c:otherwise>
		</c:choose>
	</div>
</nav>