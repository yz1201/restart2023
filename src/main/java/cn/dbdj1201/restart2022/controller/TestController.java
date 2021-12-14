package cn.dbdj1201.restart2022.controller;

import cn.dbdj1201.restart2022.entity.TestEntity;
import cn.dbdj1201.restart2022.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/listAll")
    public List<TestEntity> selectAllUsers() {
        log.info("test hot swap");
        log.info("test hot swap");
        return this.testService.selectUserList();
    }

    @GetMapping("/helloError")
    public String testHello() {
        return "hello asdasdasdas";
    }

    @GetMapping("/{id}")
    public TestEntity getUserById(@PathVariable Long id){
        return this.testService.selectUserById(id);
    }

    @GetMapping("/all")
    public List<TestEntity> listAllUsers() {

        return this.testService.selectUserList();
    }
}
