package cn.chenzw.sso.easy.server.support.handler;

import cn.chenzw.sso.easy.server.support.parser.SSOTemplateScanBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author chenzw
 */
public class SSONamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("template-scan", new SSOTemplateScanBeanDefinitionParser());
    }
}
