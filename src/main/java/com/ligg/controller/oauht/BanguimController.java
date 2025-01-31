package com.ligg.controller.oauht;

import com.ligg.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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
}
