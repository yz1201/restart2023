package cn.dbdj1201.restart2022;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.dbdj1201.restart2022.mapper")
public class Restart2022Application {

    public static void main(String[] args) {
        SpringApplication.run(Restart2022Application.class, args);
    }

}
