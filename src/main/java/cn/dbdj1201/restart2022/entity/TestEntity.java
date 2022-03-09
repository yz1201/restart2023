package cn.dbdj1201.restart2022.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: yz1201
 * @Date: 2021/12/14 14:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_user")
public class TestEntity {
    /**
     `id` int NOT NULL AUTO_INCREMENT,
      `name` varchar(10) DEFAULT NULL,
      `age` int DEFAULT NULL,
      `line1` varchar(20) DEFAULT NULL COMMENT 'test change',
    */

    private Long id;
    private String name;
    private Integer age;
    private String email;

}
