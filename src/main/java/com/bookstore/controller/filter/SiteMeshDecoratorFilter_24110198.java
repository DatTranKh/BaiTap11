package com.bookstore.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(urlPatterns = {"/*"})
public class SiteMeshDecoratorFilter_24110198 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        // 1. Bỏ qua các file tài nguyên tĩnh hoặc file bên trong thư mục decorators
        if (uri.contains("/assets/") || uri.endsWith(".css") || uri.endsWith(".js") 
                || uri.endsWith(".jpg") || uri.endsWith(".png") || uri.endsWith(".jpeg")
                || uri.contains("/decorators/")) {
            chain.doFilter(request, response);
            return;
        }

        // Bọc response để hứng nội dung HTML
        CharResponseWrapper responseWrapper = new CharResponseWrapper(resp);
        chain.doFilter(request, responseWrapper);

        // 2. NẾU SERVLET ĐÃ CHUYỂN HƯỚNG (sendRedirect) HOẶC RESPONSE ĐÃ ĐƯỢC GỬI ĐI -> DỪNG NGAY
        if (resp.isCommitted() || responseWrapper.isRedirect() || resp.getStatus() >= 300) {
            return;
        }

        // 3. Nếu không có nội dung HTML hoặc nội dung trống -> Dừng lại
        String originalContent = responseWrapper.toString();
        if (originalContent == null || originalContent.trim().isEmpty()) {
            return;
        }

        // 4. Bóc tách phần nội dung <body>...</body> để nhúng vào Decorator
        String bodyContent = extractBody(originalContent);
        req.setAttribute("pageBodyContent", bodyContent);

        // 5. Điều hướng Decorator tương ứng cho Admin hoặc User
        if (uri.contains("/admin/")) {
            req.getRequestDispatcher("/WEB-INF/views/decorators/admin.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/decorators/web.jsp").forward(req, resp);
        }
    }

    private String extractBody(String html) {
        if (html.contains("<body>") && html.contains("</body>")) {
            int start = html.indexOf("<body>") + 6;
            int end = html.indexOf("</body>");
            return html.substring(start, end);
        }
        return html;
    }

    @Override
    public void destroy() {}

    // Lớp wrapper mở rộng có theo dõi hành vi chuyển hướng (Redirect / Error)
    private static class CharResponseWrapper extends HttpServletResponseWrapper {
        private final CharArrayWriter charArray = new CharArrayWriter();
        private boolean isRedirect = false;

        public CharResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void sendRedirect(String location) throws IOException {
            this.isRedirect = true;
            super.sendRedirect(location);
        }

        @Override
        public void sendError(int sc) throws IOException {
            this.isRedirect = true;
            super.sendError(sc);
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            this.isRedirect = true;
            super.sendError(sc, msg);
        }

        public boolean isRedirect() {
            return isRedirect;
        }

        @Override
        public PrintWriter getWriter() {
            return new PrintWriter(charArray);
        }

        @Override
        public String toString() {
            return charArray.toString();
        }
    }
}