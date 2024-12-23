package com.ligg.controller.oauth;

import com.ligg.service.oauth2.BangumiOAuthService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class BangumiOAuthController {

    private final BangumiOAuthService oAuthService;

    public BangumiOAuthController(BangumiOAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    /**
     * 引导用户进行授权
     *
     * @return 授权跳转链接
     */
    @GetMapping("/login")
    public ResponseEntity<String> login() {
        String state = UUID.randomUUID().toString();// 用于防止 CSRF
        String url = oAuthService.getAuthorizationUrl(state);//生成授权 URL
        return ResponseEntity.ok(url);//返回授权 URL
    }

    /**
     * Bangumi 回调接口，用于获取 Access Token 并拉取用户信息
     *
     * @param code 授权码
     * @return 用户信息
     */
    @GetMapping("/callback")
    public ResponseEntity<Map<String, Object>> callback(@RequestParam String code) {
        String accessToken = oAuthService.getAccessToken(code);// 换取 Access Token
        if (accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Failed to get access token"));
        }
        Map<String, Object> userInfo = oAuthService.getUserInfo(accessToken);// 获取用户信息
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/user")
    public ResponseEntity<?> getUserInfo(@RequestBody Map<String, String> request) {
        String code = request.get("code");

        // 使用 code 获取 access_token
        RestTemplate restTemplate = new RestTemplate();
        String tokenUrl = "https://bgm.tv/oauth/access_token";
        Map<String, String> tokenRequest = new HashMap<>();
        tokenRequest.put("client_id", "你的App ID");
        tokenRequest.put("client_secret", "你的App Secret");
        tokenRequest.put("code", code);
        tokenRequest.put("redirect_uri", "http://127.0.0.1:5500");
        tokenRequest.put("grant_type", "authorization_code");

        try {
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenUrl, tokenRequest, Map.class);
            String accessToken = (String) tokenResponse.getBody().get("access_token");

            // 使用 access_token 获取用户信息
            String userInfoUrl = "https://api.bgm.tv/v0/me";
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);
            return ResponseEntity.ok(userInfoResponse.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "获取用户信息失败"));
        }
    }
}
