package cn.dbdj1201.restart2022.controller;

import cn.dbdj1201.hellospringbootstarter.service.TestService;
import cn.dbdj1201.restart2022.entity.TestEntity;
import cn.dbdj1201.restart2022.mapper.TestUserMapper;
import cn.dbdj1201.restart2022.service.ITestService;
import cn.dbdj1201.restart2022.service.TestMyService;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yz1201
 * @Date: 2021/12/14 16:08
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private ITestService testService;

    @Resource
    private TestUserMapper testUserMapper;

    @Autowired
    private TestMyService testMyService;

    @Autowired
    private TestService service;

    @GetMapping("/listAll")
    public List<TestEntity> selectAllUsers() {
        log.info("test hot swap");
        log.info("test hot swap");
        return this.testService.selectUserList();
    }

    @RequestMapping(value = "/dd", method = RequestMethod.GET)
    @ModelAttribute(value = "testValue")
    public String testAttribute(@RequestParam(required = false, defaultValue = "testValue") String test) {
        return test;
    }

    @GetMapping("/helloError")
    public String testHello(ModelAndView modelAndView) {
        log.info("test-{}", modelAndView.getModel());
        return "hello asdasdasdas";

    }

    @GetMapping("/{id}")
    public TestEntity getUserById(@PathVariable Long id) {
        return this.testService.selectUserById(id);
    }

    @GetMapping("/all")
    public List<TestEntity> listAllUsers() {
        return this.testService.selectUserList();
    }

    @GetMapping("/age/{minAge}/{maxAge}")
    public List<TestEntity> getUserByAge(@PathVariable int minAge, @PathVariable int maxAge) {
        log.info("minAge - {}, maxAge - {}", minAge, maxAge);
        QueryWrapper<TestEntity> queryWrapper = new QueryWrapper<>();
        String ageStr = "age";
        queryWrapper.ge(ageStr, minAge).le(ageStr, maxAge);
        List<TestEntity> testEntities = this.testUserMapper.selectList(queryWrapper);
        System.out.println(testEntities);
        return testEntities;
    }

    @GetMapping("/testUser")
    public String getUserByAge(String content) {
        return this.service.getWelcomeInfo(content);
    }

    @PostMapping("executeQuery/{minAge}/{maxAge}")
    public List<TestEntity> executeQuery(@PathVariable String minAge, @PathVariable String maxAge) {
        log.info("minAge - {}, maxAge - {}", minAge, maxAge);
        QueryWrapper<TestEntity> queryWrapper = new QueryWrapper<>();
        String ageStr = "age";
        queryWrapper.ge(ageStr, minAge).le(ageStr, maxAge);
        return this.testUserMapper.selectList(queryWrapper);
    }

    @PostMapping("addUser")
    public String addUser(@RequestBody TestEntity testEntity) {
        log.info("新增对象{}", testEntity);
        int insert = this.testUserMapper.insert(testEntity);
        return "新增对象个数" + insert;
    }

    @GetMapping("initDB")
    public String initDB() {
        int len = 1000;
        List<TestEntity> testEntities = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            TestEntity testEntity = new TestEntity();
            testEntity.setName("AI-" + RandomUtil.randomString(10) + "2023" + i);
            testEntity.setAge(i + 2023);
            testEntity.setEmail("dbdj1201@gmail.com");
            testEntities.add(testEntity);
        }
        log.info("start time");
        boolean res = this.testMyService.saveBatch(testEntities, len);
        log.info("end time");
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", res ? "200":"444");
        resMap.put("resMsg", res ? "恭喜恭喜":"444");
        resMap.put("result", "");
        return JSON.toJSONString(resMap);
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        int len = 100000;
        for (int i = 0; i < len; i++) {
            list.add(i);
        }

        List<Integer> list1 = list.subList(0, 1000);
        System.out.println(list1);


        double d = 15000000d;
        System.out.println((double) d);

        Double d1 = new Double(d);
        System.out.println(d1);
        BigDecimal bigDecimal = new BigDecimal(d);

        System.out.println(bigDecimal.doubleValue());
        NumberFormat instance = NumberFormat.getInstance();
        instance.setGroupingUsed(false);
        System.out.println(instance.format(d));

        double num = 150000000d;



    }
}
