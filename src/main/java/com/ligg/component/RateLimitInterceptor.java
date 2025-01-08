package com.ligg.component;

import com.google.common.util.concurrent.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    //每秒允许访问的次数
    private RateLimiter rateLimiter = RateLimiter.create(5.0);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (rateLimiter.tryAcquire()) {
            return true;
        } else {
            response.setStatus(429);
            response.getWriter().write("请求过于频繁");
            return false;
        }

    }
}
