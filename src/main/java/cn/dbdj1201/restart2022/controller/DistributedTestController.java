package cn.dbdj1201.restart2022.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yz1201
 * @Date: 2022/7/4 14:39
 */
@Slf4j
@RestController
public class DistributedTestController {
    @Autowired
    private StringRedisTemplate template;

    @GetMapping("/distributed/test")
    public String testDistributedAdd() {
        int len = 10;
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < len; i++) {
            executorService.submit(() -> {
                String value = UUID.randomUUID().toString();
                log.info("current uuid - {}", value);
                template.opsForValue().setIfAbsent("ysj", value);
            });
        }
        executorService.shutdown();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            log.info(e.getMessage());
        }
        return "go go go - " + template.opsForValue().get("ysj");
    }


    public static void main(String[] args) {
        Map<String, Object> m = new HashMap<>();
        List<Map<String, Object>> l = new ArrayList<>();
        Map<String, Object> item1 = new HashMap<>();
        Map<String, Object> item2 = new HashMap<>();
        Map<String, Object> item3 = new HashMap<>();
        Map<String, Object> item4 = new HashMap<>();
        item1.put("t1", "v1");
        item2.put("t2", "v2");
        item3.put("t3", "v3");
        item4.put("t4", "v4");
        l.add(item1);
        l.add(item2);
        l.add(item3);
        l.add(item4);
        m.put("test", l);
        System.out.println(m);
        List<Map<String, Object>> test = (List<Map<String, Object>>) m.get("test");
        Iterator<Map<String, Object>> iterator = test.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> next = iterator.next();
            String t1 = (String) next.get("t1");
            String t2 = (String) next.get("t2");
            if (t1 != null && !t1.isBlank()) {
                iterator.remove();
                continue;
            }
            if (t2 != null && !t2.isBlank()) {
                iterator.remove();
            }
        }
        System.out.println(m);
    }

}
