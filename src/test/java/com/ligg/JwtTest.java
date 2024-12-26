//package com.ligg;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.Claim;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.auth0.jwt.interfaces.Verification;
//import org.junit.jupiter.api.Test;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//public class JwtTest {
//    @Test
//    public void testGEn(){
//        Map<String,Object> claims = new HashMap<>();
//        claims.put("id",1);
//        claims.put("username","200309");
//        //生成jwt令牌
//        String token = JWT.create()
//                .withClaim("use",claims)//添加自定义信息
//                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*3))//设置过期
//                .sign(Algorithm.HMAC512("ligg"));//设置加密算法
//
//        System.out.println(token);
//    }
//
//    @Test
//    public void testParse(){
//
//        String token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9." +
//                "eyJ1c2UiOnsiaWQiOjEsInVzZXJuYW1lIjoiMjAwMzA5In0sImV4cCI6MTczMzQ4ODcxOX0." +
//                "CRoOm9rPkFueyuRqjn8sjuHui4_R-bKLdGqVh6W9Z5YrUeKyw6JNoGI9JHScG1C9uFmr7do3ey7JoY4zyX9iOw";
//
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512("ligg")).build();
//
//        DecodedJWT decodedJWT = jwtVerifier.verify(token);//解析
//        Map<String,Claim> claims = decodedJWT.getClaims();
//        System.out.println(claims.get("use"));
//    }
//}
//
