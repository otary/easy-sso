package cn.chenzw.sso.easy.core.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 配置属性
 *
 * @author chenzw
 */
@Configuration
@PropertySource(value = {"classpath:" + SSOConstants.CONFIG_FILE_NAME}, ignoreResourceNotFound = true)
public class SSOConstants {

    public static final String CONFIG_FILE_NAME = "sso.properties";

    public static final String KEY_SEPARATOR = "##";

    public static final String KEY_TIMESTAMP_FORMAT = "yyyyMMddHHmmss";

    public static final String APP_BASE_PACKAGE = "com.ffcs.itm.sso.server";

    public static final String DEFAULT_SSO_HANDLE_MAPPING = "/easy-sso";

    /**
     * SSO开放地址
     */
    public static String ENTRANCE_URI = DEFAULT_SSO_HANDLE_MAPPING;

    /**
     * 来源系统-标识符
     */
    public static String SOURCE_IDENTIFIER = "source";

    /**
     * 一次性密钥-标识符
     */
    public static String KEY_IDENTIFIER = "key";

    /**
     * 用户帐号-标识符
     */
    public static String USERNAME_IDENTIFIER = "username";

    /**
     * 跳转url
     */
    public static String REDIRECT_URL_IDENTIFIER = "redirectUrl";

    /**
     * 密钥
     */
    public static String PRIVATE_KEY;

    /**
     * 时效性（默认:30分钟）
     */
    public static int LIMIT_MILLI_SECOND = 1800000;

    /**
     * 默认的跳转主页
     */
    public static String DEFAULT_REDIRECT_URL = "/";

    @Value("${sso.entrance-uri:" + DEFAULT_SSO_HANDLE_MAPPING + "}")
    public void setEntranceUri(String entranceUri) {
        ENTRANCE_URI = entranceUri;
    }

    @Value("${sso.source-identifier:source}")
    public void setSourceIdentifier(String sourceIdentifier) {
        SOURCE_IDENTIFIER = sourceIdentifier;
    }

    @Value("${sso.key-identifier:key}")
    public void setKeyIdentifier(String keyIdentifier) {
        KEY_IDENTIFIER = keyIdentifier;
    }

    @Value("${sso.username-identifier:username}")
    public void setUsernameIdentifier(String usernameIdentifier) {
        USERNAME_IDENTIFIER = usernameIdentifier;
    }

    @Value("${sso.redirect-url-identifier:redirectUrl}")
    public void setRedirectUrlIdentifier(String redirectUrlIdentifier) {
        REDIRECT_URL_IDENTIFIER = redirectUrlIdentifier;
    }

    @Value("${sso.private-key:123456}")
    public void setPrivateKey(String privateKey) {
        PRIVATE_KEY = privateKey;
    }

    @Value("${sso.limit-milli-second:1800000}")
    public void setLimitMilliSecond(int limitMilliSecond) {
        LIMIT_MILLI_SECOND = limitMilliSecond;
    }

    @Value("${sso.default-redirect-url:/}")
    public void setDefaultRedirectUrl(String defaultRedirectUrl) {
        DEFAULT_REDIRECT_URL = defaultRedirectUrl;
    }


}
