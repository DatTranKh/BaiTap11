<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${isEdit ? 'Cập Nhật Sách' : 'Thêm Sách Mới'}</title>
    <style>
        .form-card { max-width: 700px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 1px 4px rgba(0,0,0,0.1); }
        .form-row { margin-bottom: 16px; }
        .form-row label { display: block; font-weight: bold; margin-bottom: 6px; color: #333; }
        .form-row input, .form-row select, .form-row textarea { width: 100%; padding: 9px 12px; border: 1px solid #ced4da; border-radius: 4px; box-sizing: border-box; }
        .btn-save { background: #007bff; color: #fff; padding: 10px 24px; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; }
        .btn-cancel { background: #6c757d; color: #fff; padding: 10px 20px; border-radius: 4px; text-decoration: none; margin-left: 10px; }
    </style>
</head>
<body>

<div class="form-card">
    <h2 style="margin-top: 0; color: #2c3e50;">${isEdit ? 'CẬP NHẬT THÔNG TIN SÁCH' : 'THÊM SÁCH MỚI'}</h2>
    
    <form action="${pageContext.request.contextPath}${isEdit ? '/admin/book-edit' : '/admin/book-add'}" method="POST">
        <c:if test="${isEdit}">
            <input type="hidden" name="bookid" value="${book.bookid}" />
        </c:if>

        <div class="form-row">
            <label for="title">Tiêu Đề Sách (*):</label>
            <input type="text" id="title" name="title" value="${book.title}" required />
        </div>

        <div class="form-row">
            <label for="isbn">Mã ISBN:</label>
            <input type="number" id="isbn" name="isbn" value="${book.isbn}" />
        </div>

        <div class="form-row">
            <label for="author_id">Tác Giả:</label>
            <select id="author_id" name="author_id">
                <c:forEach var="a" items="${authors}">
                    <option value="${a.authorId}" ${not empty book.authors && book.authors[0].authorId == a.authorId ? 'selected' : ''}>
                        ${a.authorName}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-row">
            <label for="publisher">Nhà Xuất Bản:</label>
            <input type="text" id="publisher" name="publisher" value="${book.publisher}" />
        </div>

        <div class="form-row">
            <label for="price">Giá Bán:</label>
            <input type="number" step="0.01" id="price" name="price" value="${book.price}" />
        </div>

        <div class="form-row">
            <label for="quantity">Số Lượng Trong Kho:</label>
            <input type="number" id="quantity" name="quantity" value="${book.quantity}" />
        </div>

        <div class="form-row">
            <label for="publish_date">Ngày Xuất Bản:</label>
            <input type="date" id="publish_date" name="publish_date" value="${book.publishDate}" />
        </div>

        <div class="form-row">
            <label for="cover_image">Tên File Ảnh Bìa (ví dụ: matbiec.jpg):</label>
            <input type="text" id="cover_image" name="cover_image" value="${book.coverImage}" />
        </div>

        <div class="form-row">
            <label for="description">Mô Tả Cuốn Sách:</label>
            <textarea id="description" name="description" rows="4">${book.description}</textarea>
        </div>

        <div style="margin-top: 25px;">
            <button type="submit" class="btn-save">Lưu Sách</button>
            <a href="${pageContext.request.contextPath}/admin/books" class="btn-cancel">Hủy Bỏ</a>
        </div>
    </form>
</div>

</body>
</html>