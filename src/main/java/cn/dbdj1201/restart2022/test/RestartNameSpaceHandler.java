package cn.dbdj1201.restart2022.test;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @Author: yz1201
 * @Date: 2022/3/9 13:56
 */
public class RestartNameSpaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        this.registerBeanDefinitionParser("person", new PersonBeanDefinitionParser());
    }
}
