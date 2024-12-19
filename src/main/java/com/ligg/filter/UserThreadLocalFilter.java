package com.ligg.filter;

import com.ligg.utils.ThreadLocalUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UserThreadLocalFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            // 假设从 Header 中获取用户 ID（或从 Token 解码中获取用户信息）
            String userId = httpRequest.getHeader("UserId");
            if (userId != null) {
                ThreadLocalUtil.set(userId);
            }

            chain.doFilter(request, response);
        } finally {
            // 清除 ThreadLocal，避免内存泄漏
            ThreadLocalUtil.remove();
        }
    }
}
