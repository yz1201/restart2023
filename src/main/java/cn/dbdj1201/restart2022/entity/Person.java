package cn.dbdj1201.restart2022.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: yz1201
 * @Date: 2022/3/9 13:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private Integer id;
    private String name;
    private String age;
    private String address;
}
