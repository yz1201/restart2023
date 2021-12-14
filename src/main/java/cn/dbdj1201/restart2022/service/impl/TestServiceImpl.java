package cn.dbdj1201.restart2022.service.impl;

import cn.dbdj1201.restart2022.entity.TestEntity;
import cn.dbdj1201.restart2022.mapper.TestUserMapper;
import cn.dbdj1201.restart2022.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: yz1201
 * @Date: 2021/12/14 15:32
 */
@Service
public class TestServiceImpl implements ITestService {

    @Autowired
    private TestUserMapper testMapper;

    @Override
    public List<TestEntity> selectUserList() {
        return this.testMapper.selectList(null);
    }

    @Override
    public TestEntity selectUserById(Long id) {
        return this.testMapper.selectById(id);
    }
}
