package cn.dbdj1201.restart2022.config;

import cn.dbdj1201.restart2022.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: yz1201
 * @Date: 2022/4/15 13:56
 */
@Configuration
public class TestConfiguration {

    @Bean
    public Person person(){
        return new Person();
    }

}
