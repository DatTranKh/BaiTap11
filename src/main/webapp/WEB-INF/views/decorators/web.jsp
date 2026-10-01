<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Cửa Hàng Sách</title>
    <style>
        * { box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 0; background-color: #f4f6f9; }
        .main-container { max-width: 1200px; margin: 20px auto; padding: 20px; background: #fff; border-radius: 8px; min-height: 75vh; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />

    <main class="main-container">
        ${pageBodyContent}
    </main>

    <jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>