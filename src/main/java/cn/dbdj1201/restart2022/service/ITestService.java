package cn.dbdj1201.restart2022.service;

import cn.dbdj1201.restart2022.entity.TestEntity;

import java.util.List;

/**
 * @Author: yz1201
 * @Date: 2021/12/14 15:30
 */
public interface ITestService {

    List<TestEntity> selectUserList();

    TestEntity selectUserById(Long id);

    String selectTime();
}
