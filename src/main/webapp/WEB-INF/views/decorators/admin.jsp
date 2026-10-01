<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Hệ Thống Quản Trị - Bookstore</title>
    <style>
        * { box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; display: flex; flex-direction: column; min-height: 100vh; }
        .admin-nav { background: #1a252f; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .admin-wrapper { display: flex; flex: 1; }
        .sidebar { width: 240px; background: #2c3e50; color: white; padding: 20px 0; }
        .sidebar a { display: block; color: #ecf0f1; padding: 12px 25px; text-decoration: none; border-left: 4px solid transparent; }
        .sidebar a:hover { background: #34495e; border-left-color: #3498db; }
        .admin-content { flex: 1; padding: 30px; background-color: #ecf0f1; }
    </style>
</head>
<body>
    <header class="admin-nav">
        <h3 style="margin: 0;">BẢNG QUẢN TRỊ ADMIN - MSSV: 24110198</h3>
        <div>
            <span>Xin chào Quản trị viên</span> | 
            <a href="${pageContext.request.contextPath}/home" style="color: #3498db; text-decoration: none;">Về Website</a>
        </div>
    </header>

    <div class="admin-wrapper">
        <aside class="sidebar">
            <a href="${pageContext.request.contextPath}/admin/books">Quản lý Sách (CRUD)</a>
            <a href="${pageContext.request.contextPath}/home">Xem trang chủ</a>
            <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
        </aside>

        <section class="admin-content">
            ${pageBodyContent}
        </section>
    </div>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>