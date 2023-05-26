package cn.dbdj1201.restart2022.mapper;

import cn.dbdj1201.restart2022.entity.TestEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Author: yz1201
 * @Date: 2021/12/14 15:25
 */
public interface TestUserMapper extends BaseMapper<TestEntity> {

    int insertBatch(List<TestEntity> entities);

}
