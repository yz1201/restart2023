package cn.dbdj1201.restart2022.service.impl;

import cn.dbdj1201.restart2022.service.FreeAphorismsService;
import cn.dbdj1201.restart2022.service.TranslateService;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.MD5;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yz1201
 * @Date: 2023/11/10 14:09
 */
@Service
@Slf4j
public class TranslateServiceImpl implements TranslateService {

    @Value("${baidu.translation.appId}")
    private String appId;

    @Value("${baidu.translation.secret}")
    private String secret;
    @Value("${baidu.translation.url}")
    private String translationUrl;

    @Value("${baidu.translation.salt}")
    private String translationSalt;

    @Autowired
    private FreeAphorismsService freeAphorismsService;

    @Override
    public String translate() {
        String result = null;
        //加密
        StringBuilder builder = new StringBuilder();
        String aphorisms = freeAphorismsService.getFreeAphorismsFromInternet();
        if (aphorisms == null || aphorisms.isEmpty() || aphorisms.isBlank()){
            return null;
        }
        builder.append(appId).append(aphorisms).append(translationSalt).append(secret);
        String sign = SecureUtil.md5(builder.toString());
        //根据地址获取请求
        HttpPost request = new HttpPost(translationUrl);
        request.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        // 表单参数
        List<NameValuePair> nvps = new ArrayList<>();
        // POST 请求参数
        nvps.add(new BasicNameValuePair("q", aphorisms));
        nvps.add(new BasicNameValuePair("from", "en"));
        nvps.add(new BasicNameValuePair("to", "zh"));
        nvps.add(new BasicNameValuePair("appid", appId));
        nvps.add(new BasicNameValuePair("salt", translationSalt));
        nvps.add(new BasicNameValuePair("sign", sign));
        request.setEntity(new UrlEncodedFormEntity(nvps));
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            //通过请求获取相应对象
            CloseableHttpResponse response = httpClient.execute(request);
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public static void main(String[] args) {
        String sign = "eb052961cdb48ae9514a297c01a7e5b1";
        String s = SecureUtil.md5("20231110001875793i am fine104yt2_Ag734DIpFvdl7B");
        System.out.println(s.equals(sign));
    }
}
