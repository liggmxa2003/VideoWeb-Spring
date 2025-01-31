package com.ligg.controller.oauht;

import com.ligg.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/oauht/gangumi")
public class BanguimController {

    @Value("${bangumi.client-id}")
    private String clientId;
    @Value("${bangumi.clinet-secret}")
    private String clinetSecret;
    @Value("${bangumi.redirect-uri}")
    private String redirectUri;
    @Autowired
    private StringRedisTemplate redisTemplate;
    // RestTemplate用于发送请求
    private final RestTemplate restTemplate = new RestTemplate();
    /**
     * @return banguim授权地址
     */
    @GetMapping("/login")
    public Result<String> login() {
        String banguimAuthUrl = "https://bgm.tv/oauth/authorize?client_id=" + clientId +
                "&response_type=code&redirect_uri=" + redirectUri;
        return Result.success(banguimAuthUrl);
    }

    /**
     * 1.处理授权回调
     * 2.申请 Access Token
     */
    @GetMapping("/callback")
    public Result<String> getAccessToken(String code) {
       Map<String,String> params = new HashMap<>();
       params.put("grant_type","authorization_code");
       params.put("client_id",clientId);
       params.put("client_secret",clinetSecret);
       params.put("code",code);
       params.put("redirect_uri",redirectUri);

        String tokenUrl = "https://bgm.tv/oauth/access_token";
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl,params, Map.class);
        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null || !responseBody.containsKey("access_token")){
            return Result.error("获取Access Token失败");
        }
        String accessToken = (String) responseBody.get("access_token");
        redisTemplate.opsForValue().set("access_token",accessToken);
        return Result.success(accessToken);
    }
    @GetMapping("/getUserInfo")
    public Result<String> getUserInfo() {
        String accessToken = redisTemplate.opsForValue().get("access_token");
        String userInfoUrl = "https://api.bgm.tv/v0/me";

        // 创建 HttpHeaders 对象并设置必要的请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("User-Agent", "YourAppName/1.0"); // 替换为你的应用程序名称和版本

        // 使用 HttpEntity 包装请求头
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            // 使用 exchange 方法发送请求
            ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody == null || !responseBody.containsKey("username")) {
                return Result.error("获取用户信息失败");
            }

            return Result.success(responseBody.get("username").toString());
        } catch (Exception e) {
            // 捕获异常并返回错误信息
            log.warn("请求失败: " + e.getMessage());
            return Result.error("请求失败: " + e.getMessage());
        }
    }
}
