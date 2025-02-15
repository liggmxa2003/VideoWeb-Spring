package com.ligg.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ligg.mapper.OperateLogMapper;
import com.ligg.pojo.OperateLog;
import com.ligg.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Component
@Aspect
public class LogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    ObjectMapper objectMapper;

    @Around("@annotation(com.ligg.anno.Log)")
    public Object recordLog(ProceedingJoinPoint pjp) throws Throwable {

        //操作人ID
        String token = request.getHeader("Authorization");
        if (token == null){
            return null;
        }
        Map<String, Object> user = JwtUtil.parseTokenWithValidation(token);
        Long operateUserId = (Long) user.get("id");

        //操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        //操作类名
        String className = pjp.getTarget().getClass().getName();

        //操作方法名
        String methodName = pjp.getSignature().getName();

        //操作方法参数
        Object[] args = pjp.getArgs();
        String methodParams = Arrays.toString(args);

        long begin = System.currentTimeMillis();
        //调用原始目标方法运行
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();

        //方法返回值
        String returnValue = objectMapper.writeValueAsString(result);
        //操作耗时
        Long costTime = end - begin;


        //记录日志
        OperateLog operateLog = new OperateLog(null,operateUserId,operateTime,className,methodName,methodParams,returnValue,costTime);
        operateLogMapper.insertLog(operateLog);
        log.info("AOP操作日志:[操作人ID:{},操作时间:{},操作类名:{},操作方法名:{},操作方法参数:{},操作耗时:{}]",operateUserId,operateTime,className,methodName,methodParams,costTime);
        return result;
    }

    public static void main(String[] args) {
        Map<String, Object> map = JwtUtil.parseTokenWithValidation("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsiaWQiOjI0NTAyODQ3ODYsInVzZXJuYW1lIjoibGlnZzIwMDMwOSJ9LCJleHAiOjE3MzkyNjIyMzl9.DdDdaMtDOoAHnyQypFAFBVBVJEqaDB7vY-qRzDJ2Lp0");
        System.out.println(map);
    }
}
