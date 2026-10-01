<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chi Tiết Sách #${book.bookid}</title>
    <style>
        .view-card { max-width: 650px; margin: 0 auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 1px 4px rgba(0,0,0,0.1); }
        .row-detail { display: flex; margin-bottom: 12px; border-bottom: 1px solid #eee; padding-bottom: 8px; }
        .label-col { width: 160px; font-weight: bold; color: #555; }
        .val-col { flex: 1; color: #222; }
    </style>
</head>
<body>

<div class="view-card">
    <h2>Thông Tin Chi Tiết Sách #${book.bookid}</h2>
    
    <div class="row-detail"><div class="label-col">Tiêu đề:</div><div class="val-col"><strong>${book.title}</strong></div></div>
    <div class="row-detail"><div class="label-col">Mã ISBN:</div><div class="val-col">${book.isbn}</div></div>
    <div class="row-detail">
        <div class="label-col">Tác giả:</div>
        <div class="val-col">
            <c:forEach var="a" items="${book.authors}" varStatus="st">
                ${a.authorName}${!st.last ? ', ' : ''}
            </c:forEach>
        </div>
    </div>
    <div class="row-detail"><div class="label-col">Nhà xuất bản:</div><div class="val-col">${book.publisher}</div></div>
    <div class="row-detail"><div class="label-col">Giá bán:</div><div class="val-col">${book.price}</div></div>
    <div class="row-detail"><div class="label-col">Số lượng kho:</div><div class="val-col">${book.quantity}</div></div>
    <div class="row-detail"><div class="label-col">Ngày xuất bản:</div><div class="val-col">${book.publishDate}</div></div>
    <div class="row-detail"><div class="label-col">Tên file ảnh:</div><div class="val-col">${book.coverImage}</div></div>
    <div class="row-detail"><div class="label-col">Mô tả:</div><div class="val-col">${book.description}</div></div>

    <div style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/admin/book-edit?id=${book.bookid}" style="background: #ffc107; color: #000; padding: 8px 16px; border-radius: 4px; text-decoration: none; font-weight: bold;">Chỉnh Sửa</a>
        <a href="${pageContext.request.contextPath}/admin/books" style="background: #6c757d; color: #fff; padding: 8px 16px; border-radius: 4px; text-decoration: none; margin-left: 10px;">Quay Lại</a>
    </div>
</div>

</body>
</html>