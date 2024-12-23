package com.ligg.service.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

// Bangumi OAuth2.0 服务
@Service
public class BangumiOAuthService {
    @Value("${bangumi.client-id}")
    private String clientId;//客户端id
    @Value("${bangumi.clinet-secret}")
    private String clientSecret;//客户端密钥
    @Value("${bangumi.redirect-uri}")
    private String redirectUri;//重定向地址

    private final RestTemplate restTemplate = new RestTemplate();// 用于发起 HTTP 请求

    /**
     * 获取授权 URL
     *
     * @param state 防止 CSRF 攻击的随机字符串
     * @return 用户授权的跳转 URL
     */
    public String getAuthorizationUrl(String state) {
        return "https://bgm.tv/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code"
                + "&state=" + state;
    }

    /**
     * 通过授权码换取 Access Token
     *
     * @param code 用户授权后返回的临时授权码
     * @return Access Token
     */
    public String getAccessToken(String code) {
        String tokenUrl = "https://bgm.tv/oauth/access_token";

        // POST 请求体
        Map<String, String> requestBody = Map.of(
                "grant_type", "authorization_code",
                "client_id", clientId,
                "client_secret", clientSecret,
                "code", code,
                "redirect_uri", redirectUri
        );

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 发起 POST 请求获取 Access Token
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        Map<String, Object> responseBody = response.getBody();
        return responseBody != null ? (String) responseBody.get("access_token") : null;
    }

    /**
     * 获取用户信息
     * @param accessToken 用于认证的 Access Token
     * @return 用户信息
     */
    public Map<String,Object> getUserInfo(String accessToken){
        String userInfoUrl = "https://api.bgm.tv/v0/me";

        // 设置请求头，携带 Access Token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer" + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET,request, Map.class);

        return response.getBody();
    }
}
