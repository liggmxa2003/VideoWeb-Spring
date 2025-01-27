package com.ligg.component;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
// 限流拦截器
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RateLimiter rateLimiter;

    public RateLimitInterceptor() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(100)
                // 限制刷新周期为1秒，以减少刷新频率并避免过度使用资源
                .limitRefreshPeriod(Duration.ofSeconds(2))
                .timeoutDuration(Duration.ZERO)
                .build();
        this.rateLimiter = RateLimiterRegistry.of(config).rateLimiter("api");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (rateLimiter.acquirePermission()) {
            return true;
        } else {
            response.setStatus(429);
            response.getWriter().write("请求过于频繁");
            return false;
        }
    }
}
