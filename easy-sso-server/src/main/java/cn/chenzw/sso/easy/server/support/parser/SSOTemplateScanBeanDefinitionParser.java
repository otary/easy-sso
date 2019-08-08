package cn.chenzw.sso.easy.server.support.parser;

import cn.chenzw.sso.easy.server.ext.SSO;
import cn.chenzw.sso.easy.server.support.SSOTemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * SSO模版扫描器（XML配置方式）
 *
 * @author chenzw
 */
public class SSOTemplateScanBeanDefinitionParser implements BeanDefinitionParser {

    private static final Logger logger = LoggerFactory.getLogger(SSOTemplateScanBeanDefinitionParser.class);

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        logger.debug("start scan sso template...");

        long t1 = System.currentTimeMillis();

        String basePackage = element.getAttribute("base-package");
        basePackage = parserContext.getReaderContext().getEnvironment().resolvePlaceholders(basePackage);
        String[] basePackages = StringUtils.tokenizeToStringArray(basePackage, ",; \t\n");

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(SSO.class));
        Set<BeanDefinition> beans = new LinkedHashSet<>();
        for (String _basePackage : basePackages) {
            beans.addAll(scanner.findCandidateComponents(_basePackage));
        }
        logger.debug("find {} sso template:[{}]", beans.size(), beans);

        for (BeanDefinition bean : beans) {
            parserContext.getReaderContext().getRegistry().registerBeanDefinition(bean.getBeanClassName(), bean);
            SSOTemplateFactory.register(bean.getBeanClassName());
        }

        logger.debug("finish scan sso template, cost [{}] ms", (System.currentTimeMillis() - t1));

        return null;
    }


}
