package cn.dbdj1201.restart2022.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
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
        }catch (InterruptedException e){
            log.info(e.getMessage());
        }
        return "go go go - " + template.opsForValue().get("ysj");
    }

}
