package cn.chenzw.easy.sso.server.support;


import cn.itm.ffcs.sso.server.constants.SSOConstants;
import cn.itm.ffcs.sso.server.exception.SSOException;
import cn.itm.ffcs.sso.server.ext.SSO;
import cn.itm.ffcs.sso.server.utils.HttpHolder;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;


/**
 * @author chenzw
 */
public final class SSOTemplateFactory {

    private static final Logger logger = LoggerFactory.getLogger(SSOTemplateFactory.class);

    private static Map<String, String> ssoTemplates = new HashMap<String, String>();

    public static void register(String source, String className) {
        if (ssoTemplates.containsKey(source)) {
            logger.warn("find mutiple source template:[{}]", StringUtils.join(ssoTemplates.get(source), className));
        }
        ssoTemplates.put(source, className);
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
        if (ssoTemplates.containsKey(source)) {
            String templateClassName = ssoTemplates.get(source);
            WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
            if (context.containsBean(templateClassName)) {
                Object bean = context.getBean(templateClassName);
                if (bean instanceof AbstractSSOTemplate) {
                    logger.info("[{}] use sso template: [{}]!", source, bean);
                    return (AbstractSSOTemplate) bean;
                }
            }
        }
        logger.info("[{}] use default sso template!", source);
        return new DefaultSSOTemplate();

    }

    public static AbstractSSOTemplate autoGetTemplate() {
        try {
            String source = ServletRequestUtils
                    .getStringParameter(HttpHolder.REQUEST.get(), SSOConstants.SOURCE_IDENTIFIER);
            return getTemplate(source);
        } catch (ServletRequestBindingException e) {
            throw new SSOException("获取source参数异常!");
        }

    }
}
