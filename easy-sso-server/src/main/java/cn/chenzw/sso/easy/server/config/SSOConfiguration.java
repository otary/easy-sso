package cn.chenzw.sso.easy.server.config;

import cn.chenzw.sso.easy.server.constants.SSOConstants;
import cn.chenzw.sso.easy.server.support.AbstractSSOTemplate;
import cn.chenzw.sso.easy.server.support.SSOTemplateFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Map;


@Configuration
@Import({SSOConstants.class})
@ComponentScan(basePackages = SSOConstants.APP_BASE_PACKAGE)
public class SSOConfiguration implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Map<String, AbstractSSOTemplate> beans = configurableListableBeanFactory.getBeansOfType(AbstractSSOTemplate.class);

        for (Map.Entry<String, AbstractSSOTemplate> ssoTemplateEntry : beans.entrySet()) {
            SSOTemplateFactory.register(ssoTemplateEntry.getKey(), ssoTemplateEntry.getValue());
        }
    }
}
