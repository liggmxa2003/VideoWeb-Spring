package com.ligg.config;

import com.ligg.interceptors.LoginInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

//Web配置
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptors loginInterceptors;

   // 拦截器配置
@Override
public void addInterceptors(InterceptorRegistry registry) {
    // 登录拦截器
    registry.addInterceptor(loginInterceptors)
            .order(1) // 设置顺序
            .excludePathPatterns(getExcludePaths())
            .addPathPatterns("/user/**", "/uploadVideo", "/upload");

    // 其他拦截器...
}

// 集中管理放行路径
private List<String> getExcludePaths() {
    return Arrays.asList(
            "/user",
            "/user/login",
            "/user/register",
            "/user/resetPassword",
            "/email",
            "/video/**"
    );
}

    //配置跨域
    /**
     * 添加跨域资源共享(CORS)映射配置
     * 该方法用于配置允许跨越请求，使得前端应用可以与后端服务进行交互
     * 主要配置内容包括：允许所有域的请求、支持的HTTP方法、是否允许发送凭证、预检请求的缓存时间、允许的请求头
     *
     * @param registry CorsRegistry对象，用于注册CORS映射
     */
    /*@Override
    public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
        registry.addMapping("/**") // 配置允许跨越请求的路径，"/**"表示所有路径
                .allowedOriginPatterns("*") // 允许所有域的请求，"*"表示通配符
                .allowedMethods("GET","POST","PUT","DELETE") // 允许的HTTP方法
                .allowCredentials(true) // 允许发送凭证，例如cookies
                .maxAge(3600) // 预检请求的缓存时间，单位为秒
                .allowedHeaders("*"); // 允许的请求头，"*"表示所有头
    }*/
}
