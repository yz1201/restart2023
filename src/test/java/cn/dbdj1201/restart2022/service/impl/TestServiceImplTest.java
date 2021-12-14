package cn.dbdj1201.restart2022.service.impl;

import cn.dbdj1201.restart2022.mapper.TestUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: yz1201
 * @Date: 2021/12/14 15:40
 */
@SpringBootTest
class TestServiceImplTest {

    @Autowired
    private TestUserMapper userMapper;

    @Test
    void selectUserList() {
        System.out.println(this.userMapper.selectList(null));
    }
}