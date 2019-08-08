package cn.chenzw.sso.easy.server.support;


import cn.chenzw.sso.easy.server.constants.SSOConstants;
import cn.chenzw.sso.easy.server.exception.SSOException;
import cn.chenzw.sso.easy.server.ext.SSO;
import cn.chenzw.toolkit.http.HttpHolder;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * @author chenzw
 */
public final class SSOTemplateFactory {

    private static final Logger logger = LoggerFactory.getLogger(SSOTemplateFactory.class);

    private static Map<String, String> ssoTemplates = new HashMap<String, String>();

    public static void register(String source, String beanName) {
        if (ssoTemplates.containsKey(source)) {
            logger.warn("find mutiple source template:[{}]", StringUtils.join(ssoTemplates.get(source), beanName));
        }
        ssoTemplates.put(source, beanName);
    }

    public static void register(String beanName, AbstractSSOTemplate ssoTemplate) {
        if (!ssoTemplate.getClass().isAnnotationPresent(SSO.class)) {
            throw new SSOException("Class [" + ssoTemplate.getClass().getName() + "] missing @SSO annotation");
        }
        SSO sso = ssoTemplate.getClass().getAnnotation(SSO.class);
        if (ArrayUtils.isNotEmpty(sso.source())) {
            String[] sources = sso.source();
            for (String source : sources) {
                register(source, beanName);
            }
        }

    }

    public static void register(String className) {
        try {
            Class<?> templateCls = Thread.currentThread().getContextClassLoader().loadClass(className);
            SSO sso = templateCls.getAnnotation(SSO.class);
            if (ArrayUtils.isNotEmpty(sso.source())) {
                String[] sources = sso.source();
                for (String source : sources) {
                    register(source, className);
                }
            }
        } catch (ClassNotFoundException e) {
            logger.error("未找到对应的模版类:[{}]", className);
            e.printStackTrace();
        }
    }

    public static AbstractSSOTemplate getTemplate(String source) {
        // 根据source动态选择处理类
        if (!ssoTemplates.containsKey(source)) {
            if (ssoTemplates.containsKey("*")) {
                source = "*";
            }
        }

        AbstractSSOTemplate ssoTemplate = getTemplateBySource(source);
        if (ssoTemplate == null) {
            throw new SSOException("未找到对应的单点处理类");
        }
        return ssoTemplate;
    }

    public static AbstractSSOTemplate autoGetTemplate() {
        try {
            String source = ServletRequestUtils
                    .getStringParameter(HttpHolder.getRequest(), SSOConstants.SOURCE_IDENTIFIER);
            return getTemplate(source);
        } catch (ServletRequestBindingException e) {
            throw new SSOException("获取source参数异常!");
        }
    }

    private static AbstractSSOTemplate getTemplateBySource(String source) {
        String beanName = ssoTemplates.get(source);

        WebApplicationContext context = RequestContextUtils.findWebApplicationContext(HttpHolder.getRequest());
        Object bean = context.getBean(beanName);
        if (bean instanceof AbstractSSOTemplate) {
            logger.info("[{}] use sso template: [{}]!", source, bean);
            return (AbstractSSOTemplate) bean;
        }
        return null;
    }
}
