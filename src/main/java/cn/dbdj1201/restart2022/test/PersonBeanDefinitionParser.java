package cn.dbdj1201.restart2022.test;

import cn.dbdj1201.restart2022.entity.Person;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @Author: yz1201
 * @Date: 2022/3/9 14:06
 */
public class PersonBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {


    @Override
    protected Class<?> getBeanClass(Element element) {
        return Person.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");
        String id = element.getAttribute("id");
        String age = element.getAttribute("age");
        String address = element.getAttribute("address");
        if (StringUtils.hasText(age)) {
            builder.addPropertyValue("age", Integer.parseInt(age));
        }

        if (StringUtils.hasText(id)) {
            builder.addPropertyValue("id", Integer.parseInt(id));
        }


        if (StringUtils.hasText(name)) {
            builder.addPropertyValue("name", name);
        }

        if (StringUtils.hasText(address)) {
            builder.addPropertyValue("address", address);
        }
    }
}
