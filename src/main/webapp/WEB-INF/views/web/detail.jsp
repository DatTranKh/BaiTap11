<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chi Tiết Sách - ${book.title}</title>
    <style>
        .detail-wrapper {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 100%;
            margin: 25px auto;
        }

        /* Khung bảng theo cấu trúc đề thi */
        .detail-table {
            width: 100%;
            max-width: 850px;
            border-collapse: collapse;
            border: 1px solid #000;
            background: #fff;
        }

        .detail-table td {
            border: 1px solid #000;
            padding: 12px 16px;
            vertical-align: top;
        }

        .cover-cell {
            width: 38%;
            text-align: center;
            vertical-align: middle;
            background-color: #fafafa;
        }

        .cover-cell img {
            max-width: 180px;
            max-height: 250px;
            object-fit: cover;
        }

        .info-cell {
            width: 62%;
            line-height: 1.8;
            font-size: 15px;
        }

        .section-header-cell {
            font-size: 16px;
            font-weight: bold;
            background-color: #ffffff;
            padding: 8px 16px !important;
        }

        .review-row-cell {
            padding: 10px 16px !important;
            font-size: 15px;
            background-color: #ffffff;
        }

        .form-review-cell {
            padding: 16px !important;
            background-color: #ffffff;
        }

        .form-review-title {
            font-weight: bold;
            margin-bottom: 10px;
            font-size: 15px;
        }

        .review-textarea {
            width: 100%;
            height: 80px;
            padding: 10px;
            border: 1px solid #888;
            box-sizing: border-box;
            font-family: inherit;
            font-size: 14px;
            resize: vertical;
        }

        .form-actions {
            margin-top: 10px;
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .btn-submit {
            background-color: #efefef;
            color: #000;
            border: 1px solid #767676;
            padding: 5px 18px;
            font-size: 14px;
            cursor: pointer;
        }

        .btn-submit:hover {
            background-color: #e0e0e0;
        }

        .login-notice {
            color: #d9534f;
            font-style: italic;
            font-size: 14px;
        }
    </style>
</head>
<body>

<div class="detail-wrapper">
    <table class="detail-table">
        <!-- 1. Hàng thông tin chính của sách -->
        <tr>
            <!-- [cover_image] -->
            <td class="cover-cell">
                <c:choose>
                    <c:when test="${not empty book.coverImage}">
                        <img src="${pageContext.request.contextPath}/assets/images/${book.coverImage}" 
                             alt="[cover_image]" 
                             onerror="this.parentElement.innerHTML='[cover_image]';" />
                    </c:when>
                    <c:otherwise>
                        <span>[cover_image]</span>
                    </c:otherwise>
                </c:choose>
            </td>

            <!-- Cột thông tin chi tiết -->
            <td class="info-cell">
                <div><strong>Tiêu đề:</strong> ${book.title}</div>
                <div><strong>Mã isbn:</strong> ${book.isbn}</div>
                <div>
                    <strong>Tác giả:</strong>
                    <c:forEach var="a" items="${book.authors}" varStatus="status">
                        ${a.authorName}${!status.last ? ', ' : ''}
                    </c:forEach>
                </div>
                <div><strong>Publisher:</strong> ${book.publisher}</div>
                <div><strong>Publisher_date:</strong> ${book.publishDate}</div>
                <div><strong>Quantity:</strong> ${book.quantity}</div>
                <div><strong>Reviews (${book.reviewCount})</strong></div>
            </td>
        </tr>

        <!-- 2. Hàng tiêu đề 'Reviews' -->
        <tr>
            <td colspan="2" class="section-header-cell">
                Reviews
            </td>
        </tr>

        <!-- 3. Danh sách các bình luận theo mẫu: [users]: [review_text] -->
        <c:choose>
            <c:when test="${not empty ratings}">
                <c:forEach var="r" items="${ratings}">
                    <tr>
                        <td colspan="2" class="review-row-cell">
                            [${r.userFullname}]: ${r.reviewText}
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="2" class="review-row-cell" style="color: #777; font-style: italic;">
                        Chưa có đánh giá nào cho cuốn sách này.
                    </td>
                </tr>
            </c:otherwise>
        </c:choose>

        <!-- 4. Hàng chứa 'Form thêm reviews' & nút '[Submit]' -->
        <tr>
            <td colspan="2" class="form-review-cell">
                <div class="form-review-title">Form thêm reviews</div>

                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        <form action="${pageContext.request.contextPath}/book-detail" method="POST">
                            <input type="hidden" name="bookid" value="${book.bookid}" />
                            
                            <textarea name="review_text" class="review-textarea" required placeholder="Nhập nội dung review..."></textarea>
                            
                            <div class="form-actions">
                                <div>
                                    <label for="ratingSelect">Đánh giá sao:</label>
                                    <select id="ratingSelect" name="rating">
                                        <option value="5">5 sao</option>
                                        <option value="4">4 sao</option>
                                        <option value="3">3 sao</option>
                                        <option value="2">2 sao</option>
                                        <option value="1">1 sao</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn-submit">[Submit]</button>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <p class="login-notice">
                            Vui lòng <a href="${pageContext.request.contextPath}/login">Đăng nhập</a> bằng tài khoản User để gửi đánh giá cho cuốn sách này
                        </p>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
</div>

</body>
</html>