package cn.dbdj1201.restart2022.service.impl;

import cn.dbdj1201.restart2022.entity.Aphorisms;
import cn.dbdj1201.restart2022.service.FreeAphorismsService;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yz1201
 * @Date: 2023/11/10 14:16
 */
@Slf4j
@Service
public class FreeAphorismsServiceImpl implements FreeAphorismsService {

    @Value("${api.aphorisms.url}")
    private String aphorismsUrl;

    @Override
    public String getFreeAphorismsFromInternet() {
        String aphorismsText = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String result = null;
            //拼接url
            String url = aphorismsUrl;
            //根据地址获取请求
            HttpGet request = new HttpGet(url);
            //通过请求获取相应对象
            CloseableHttpResponse response = httpClient.execute(request);
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
                Aphorisms aphorisms = JSON.parseObject(result, Aphorisms.class);
                aphorismsText = aphorisms.getContent();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return aphorismsText;
    }
}
