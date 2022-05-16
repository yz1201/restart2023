package cn.dbdj1201.restart2022.controller;

import cn.dbdj1201.restart2022.entity.TestEntity;
import cn.dbdj1201.restart2022.service.ITestService;
import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Paths;
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
    public Object helloUsers(@RequestParam Long id){
        TestEntity testEntity = this.testService.selectUserById(id);
        return testEntity;
    }

    @GetMapping("/test")
    public Object test(String content){
        Map<String,String> map = new HashMap<>();
        map.put("content",content+" hei hei");
        map.put("path",Paths.get("/home/dbdj1201/test").toString());
        return map;
    }




}
