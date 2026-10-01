<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản Lý Sách</title>
    <style>
        .admin-card { background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); }
        .card-header-actions { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .btn-add { background: #27ae60; color: #fff; text-decoration: none; padding: 9px 18px; border-radius: 5px; font-weight: bold; }
        .btn-add:hover { background: #219150; }
        
        .admin-table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        .admin-table th, .admin-table td { border: 1px solid #dee2e6; padding: 10px 12px; text-align: left; vertical-align: middle; }
        .admin-table th { background-color: #f1f3f5; color: #495057; }
        .img-thumb { width: 50px; height: 65px; object-fit: cover; border-radius: 3px; border: 1px solid #ccc; }
        
        .action-btns { display: flex; gap: 6px; }
        .btn-sm { padding: 4px 10px; border-radius: 3px; text-decoration: none; font-size: 13px; color: #fff; }
        .btn-view { background: #17a2b8; }
        .btn-edit { background: #ffc107; color: #212529; }
        .btn-delete { background: #dc3545; }
        
        .pagination-container { display: flex; justify-content: center; align-items: center; gap: 8px; margin-top: 20px; }
        .pagination-container a { padding: 6px 12px; border: 1px solid #ced4da; text-decoration: none; color: #007bff; border-radius: 4px; }
        .pagination-container a.active { background: #007bff; color: #fff; border-color: #007bff; font-weight: bold; }
        .pagination-container span.disabled { padding: 6px 12px; color: #adb5bd; border: 1px solid #dee2e6; border-radius: 4px; }
    </style>
</head>
<body>

<div class="admin-card">
    <div class="card-header-actions">
        <h2 style="margin: 0; color: #2c3e50;">Danh Sách Sách (Tổng: ${totalBooks})</h2>
        <a href="${pageContext.request.contextPath}/admin/book-add" class="btn-add">+ Thêm Sách Mới</a>
    </div>

    <table class="admin-table">
        <thead>
            <tr>
                <th style="width: 50px;">ID</th>
                <th style="width: 60px;">Bìa</th>
                <th>Tiêu Đề</th>
                <th style="width: 90px;">Mã ISBN</th>
                <th>Tác Giả</th>
                <th>Nhà Xuất Bản</th>
                <th style="width: 90px;">Giá</th>
                <th style="width: 60px;">SL</th>
                <th style="width: 170px;">Thao Tác</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="b" items="${books}">
                <tr>
                    <td>${b.bookid}</td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty b.coverImage}">
                                <img src="${pageContext.request.contextPath}/assets/images/${b.coverImage}" class="img-thumb" alt="Cover" />
                            </c:when>
                            <c:otherwise><span style="color: #999; font-size: 11px;">Không bìa</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td><strong>${b.title}</strong></td>
                    <td>${b.isbn}</td>
                    <td>
                        <c:forEach var="a" items="${b.authors}" varStatus="st">
                            ${a.authorName}${!st.last ? ', ' : ''}
                        </c:forEach>
                    </td>
                    <td>${b.publisher}</td>
                    <td>${b.price}</td>
                    <td>${b.quantity}</td>
                    <td>
                        <div class="action-btns">
                            <a href="${pageContext.request.contextPath}/admin/book-view?id=${b.bookid}" class="btn-sm btn-view">Xem</a>
                            <a href="${pageContext.request.contextPath}/admin/book-edit?id=${b.bookid}" class="btn-sm btn-edit">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/book-delete?id=${b.bookid}" 
                               class="btn-sm btn-delete" 
                               onclick="return confirm('Bạn có chắc muốn xóa cuốn sách: ${b.title}?');">Xóa</a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Thanh phân trang cho trang Admin[cite: 1] -->
    <div class="pagination-container">
        <c:choose>
            <c:when test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/admin/books?page=${currentPage - 1}">Trang trước</a>
            </c:when>
            <c:otherwise><span class="disabled">Trang trước</span></c:otherwise>
        </c:choose>

        <c:forEach var="p" begin="1" end="${totalPages}">
            <c:choose>
                <c:when test="${p == currentPage}">
                    <a href="#" class="active">${p}</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/admin/books?page=${p}">${p}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:choose>
            <c:when test="${currentPage < totalPages}">
                <a href="${pageContext.request.contextPath}/admin/books?page=${currentPage + 1}">Trang sau</a>
            </c:when>
            <c:otherwise><span class="disabled">Trang sau</span></c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>