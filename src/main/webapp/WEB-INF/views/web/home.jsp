<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Trang Chủ - Danh Sách Sách</title>
<style>
/* Khung bao ngoài để căn toàn bộ nội dung vào chính giữa màn hình */
.home-page-container {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	width: 100%;
	margin: 20px auto;
}

/* Thanh chọn tác giả căn giữa */
.author-select-bar {
	width: 100%;
	max-width: 900px;
	margin-bottom: 20px;
	display: flex;
	align-items: center;
	justify-content: flex-start;
	gap: 12px;
	background: #f8f9fa;
	padding: 12px 20px;
	border-radius: 6px;
	border: 1px solid #ced4da;
	box-sizing: border-box;
}

.author-select-bar select {
	padding: 8px 14px;
	font-size: 15px;
	border-radius: 4px;
	border: 1px solid #adb5bd;
	outline: none;
	cursor: pointer;
}

/* Bảng hiển thị cố định độ rộng và căn giữa trang */
.content-table {
	width: 100%;
	max-width: 900px;
	border-collapse: collapse;
	margin: 0 auto;
	border: 1px solid #333;
	background: #fff;
	table-layout: fixed; /* Giữ 3 cột sản phẩm đều nhau tuyệt đối */
}

.content-table th, .content-table td {
	border: 1px solid #333;
	padding: 14px;
	vertical-align: top;
}

/* Dòng hiển thị tiêu đề tác giả */
.author-header {
	font-size: 17px;
	font-weight: bold;
	background-color: #ffffff;
	text-align: left;
	padding: 12px 16px;
}

/* 3 cột sản phẩm phân bố đều 1/3 bề rộng */
.book-col {
	width: 33.33%;
	box-sizing: border-box;
	background-color: #fff;
}

/* Khung hiển thị ảnh bìa căn giữa trong từng cột */
.cover-box {
	text-align: center;
	margin: 0 auto 12px auto;
	width: 100%;
	height: 180px;
	display: flex;
	align-items: center;
	justify-content: center;
	background-color: #f8f9fa;
	border: 1px dashed #ced4da;
	border-radius: 4px;
}

.cover-box img {
	max-width: 100%;
	max-height: 170px;
	object-fit: contain;
}

/* Các dòng thông tin chi tiết của từng cuốn sách */
.book-info-row {
	margin-bottom: 7px;
	line-height: 1.5;
	font-size: 14px;
	color: #212529;
	word-wrap: break-word;
}

.book-title-link {
	font-weight: bold;
	color: #0056b3;
	text-decoration: none;
}

.book-title-link:hover {
	text-decoration: underline;
}

/* Dòng phân trang: Căn giữa tuyệt đối ở đáy bảng */
.pagination-row {
	text-align: center !important;
	vertical-align: middle !important;
	padding: 15px !important;
	background-color: #ffffff;
}

.pagination-wrapper {
	display: inline-flex;
	align-items: center;
	justify-content: center;
	gap: 10px;
	font-size: 15px;
}

.pagination-wrapper a {
	color: #0056b3;
	text-decoration: none;
	padding: 4px 8px;
	border-radius: 3px;
}

.pagination-wrapper a:hover {
	background-color: #e9ecef;
	text-decoration: none;
}

.pagination-wrapper a.active {
	font-weight: bold;
	color: #dc3545;
	border-bottom: 2px solid #dc3545;
}

.pagination-disabled {
	color: #adb5bd;
	padding: 4px 8px;
	cursor: not-allowed;
	user-select: none;
}
</style>
</head>
<body>

	<div class="home-page-container">

		<!-- Thanh chuyển đổi xem theo từng tác giả[cite: 1, 4] -->
		<div class="author-select-bar">
			<label for="authorFilter"><strong>Tác giả:</strong></label> <select
				id="authorFilter"
				onchange="window.location.href='${pageContext.request.contextPath}/home?authorId=' + this.value">
				<c:forEach var="a" items="${authors}">
					<option value="${a.authorId}"
						${a.authorId == selectedAuthor.authorId ? 'selected' : ''}>
						${a.authorName}</option>
				</c:forEach>
			</select>
		</div>

		<!-- Bảng nội dung chính căn giữa trang -->
		<table class="content-table">
			<!-- 1. Dòng hiển thị: Tác giả :Author name -->
			<tr>
				<td colspan="3" class="author-header">Tác giả :
					${selectedAuthor != null ? selectedAuthor.authorName : 'Chưa có thông tin'}
				</td>
			</tr>

			<!-- 2. Dòng 3 cột hiển thị sản phẩm sách -->
			<tr>
				<c:forEach var="book" items="${books}">
					<td class="book-col">
						<!-- [cover_image] -->
						<div class="cover-box">
							<c:choose>
								<c:when test="${not empty book.coverImage}">
									<img
										src="${pageContext.request.contextPath}/assets/images/${book.coverImage}"
										alt="[cover_image]"
										onerror="this.parentElement.innerHTML='[cover_image]';" />
								</c:when>
								<c:otherwise>
									<span>[cover_image]</span>
								</c:otherwise>
							</c:choose>
						</div> <!-- Tiêu đề: liên kết tới trang chi tiết ở Câu 4[cite: 1, 4] -->
						<div class="book-info-row">
							<strong>Tiêu đề:</strong> <a class="book-title-link"
								href="${pageContext.request.contextPath}/book-detail?id=${book.bookid}">
								${book.title} </a>
						</div> <!-- Mã isbn: -->
						<div class="book-info-row">
							<strong>Mã isbn:</strong> ${book.isbn}
						</div> <!-- Tác giả: -->
						<div class="book-info-row">
							<strong>Tác giả:</strong>
							<c:choose>
								<c:when test="${not empty book.authors}">
									<c:forEach var="ba" items="${book.authors}" varStatus="status">
                                    ${ba.authorName}${!status.last ? ', ' : ''}
                                </c:forEach>
								</c:when>
								<c:otherwise>
                                ${selectedAuthor.authorName}
                            </c:otherwise>
							</c:choose>
						</div> <!-- Publisher: -->
						<div class="book-info-row">
							<strong>Publisher:</strong> ${book.publisher}
						</div> <!-- Publisher_date: -->
						<div class="book-info-row">
							<strong>Publisher date:</strong> ${book.publishDate}
						</div> <!-- Quantity: -->
						<div class="book-info-row">
							<strong>Quantity:</strong> ${book.quantity}
						</div> <!-- Review (10) -->
						<div class="book-info-row">
							<strong>Review (${book.reviewCount})</strong>
							<form action="${pageContext.request.contextPath}/cart-add"
								method="POST" style="margin-top: 8px;">
								<input type="hidden" name="bookid" value="${book.bookid}" />
								<button type="submit"
									style="background-color: #28a745; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer; font-size: 13px; width: 100%;">
									+ Thêm vào giỏ</button>
							</form>
						</div>
					</td>
				</c:forEach>

				<!-- Giữ đều 3 cột nếu trang cuối cùng có ít hơn 3 cuốn -->
				<c:if test="${books.size() < 3}">
					<c:forEach begin="1" end="${3 - books.size()}">
						<td class="book-col" style="background-color: #ffffff;"></td>
					</c:forEach>
				</c:if>
			</tr>

			<!-- 3. Dòng phân trang căn giữa dưới cùng: Trang trước – 1 2 3 4 – Trang sau -->
			<tr>
				<td colspan="3" class="pagination-row">
					<div class="pagination-wrapper">
						<!-- Nút 'Trang trước' -->
						<c:choose>
							<c:when test="${currentPage > 1}">
								<a
									href="${pageContext.request.contextPath}/home?authorId=${selectedAuthor.authorId}&page=${currentPage - 1}">Trang
									trước</a>
							</c:when>
							<c:otherwise>
								<span class="pagination-disabled">Trang trước</span>
							</c:otherwise>
						</c:choose>

						<span>–</span>

						<!-- Dãy số trang: 1 2 3 4 -->
						<c:forEach var="p" begin="1" end="${totalPages}">
							<c:choose>
								<c:when test="${p == currentPage}">
									<a href="#" class="active">${p}</a>
								</c:when>
								<c:otherwise>
									<a
										href="${pageContext.request.contextPath}/home?authorId=${selectedAuthor.authorId}&page=${p}">${p}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>

						<span>–</span>

						<!-- Nút 'Trang sau' -->
						<c:choose>
							<c:when test="${currentPage < totalPages}">
								<a
									href="${pageContext.request.contextPath}/home?authorId=${selectedAuthor.authorId}&page=${currentPage + 1}">Trang
									sau</a>
							</c:when>
							<c:otherwise>
								<span class="pagination-disabled">Trang sau</span>
							</c:otherwise>
						</c:choose>
					</div>
				</td>
			</tr>
		</table>

	</div>

</body>
</html>