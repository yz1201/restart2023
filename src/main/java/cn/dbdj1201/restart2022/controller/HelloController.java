package cn.dbdj1201.restart2022.controller;

import cn.dbdj1201.restart2022.entity.TestEntity;
import cn.dbdj1201.restart2022.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.text.ChoiceFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: yz1201
 * @Date: 2021/12/21 14:54
 */
@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private ITestService testService;

    @GetMapping("/user")
    public Object helloUsers(@RequestParam Long id) {
        return this.testService.selectUserById(id);
    }

    @GetMapping("/test")
    public Object test(String content) {
        Map<String, String> map = new HashMap<>();
        map.put("content", content + " hei hei");
        map.put("path", Paths.get("/home/dbdj1201/test").toString());
        return map;
    }

    @GetMapping("/decorate/{amount}")
    public String decorate(@PathVariable String amount) {
        int amountVal = Integer.parseInt(amount);
        String resAmt;
        /*
        10 100 1000 1100 1110 1111  10000 11000 11100 11110 11111
         */
        Format decimalFormat;
//        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        if (amountVal >= 10000) {
//            if (amountVal % 10000 == 0){
//                decimalFormat = new DecimalFormat("0.00");
//                amountVal /= 10000;
//            }else{
//                decimalFormat =
//            }

            decimalFormat = new DecimalFormat("0.00");
            amountVal /= 10000;
        } else {
//            decimalFormat = new DecimalFormat("#,###.");
            decimalFormat = new ChoiceFormat("#,###.##");
        }
        resAmt = decimalFormat.format(amountVal);
        return resAmt;
    }


    private String formatAmt(int amt) {

        /*
        10000
        11000
        11111
        11100
        11110 lack rules
         */
        String[] units = {"元", "万元"};
        String res = null;
        if (amt >= 10000) {
            if (amt % 10 == 0) {
                res = amt / 10000 + units[1];
            } else {

            }

        }

        return res;
    }

    @GetMapping("/now")
    public String now() {
        return this.testService.selectTime();
    }

    @GetMapping("/cuteThing")
    public Map<String, Object> help() {
        //https://api.thecatapi.com/v1/images/search?size=full
        try {
            //拼接url
            String url = "https://api.thecatapi.com/v1/images/search?size=full";
            //根据地址获取请求
            HttpGet request = new HttpGet(url);
            //获取当前客户端对象
            CloseableHttpClient httpClient = HttpClients.custom().build();
            //通过请求获取相应对象
            CloseableHttpResponse response = httpClient.execute(request);
            // 判断网络连接状态码是否正常(0--200都数正常)
            String result = null;
            if (response.getCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
            Map<String,Object> res = new HashMap<>();
            res.put("code","200");
            res.put("result", result);
            res.put("", "");
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
