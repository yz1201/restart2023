package cn.dbdj1201.restart2022.test;

import cn.dbdj1201.restart2022.entity.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: yz1201
 * @Date: 2022/3/9 13:48
 */
public class Test20220309 {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//        Person person = (Person) ac.getBean("person");
        Person person = ac.getBean(Person.class);
        System.out.println(person);
    }
}
