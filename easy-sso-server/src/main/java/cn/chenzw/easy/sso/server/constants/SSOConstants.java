package cn.chenzw.easy.sso.server.constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 固定值
 * @author chenzw
 */
public final class SSOConstants {


    private SSOConstants() {
    }


    public static final String CONFIG_FILE_NAME = "sso.properties";

    /**
     * 来源系统-标识符
     */
    public static final String SOURCE_IDENTIFIER;

    /**
     *  一次性密钥-标识符
     */
    public static final String KEY_IDENTIFIER;

    /**
     * 用户帐号-标识符
     */
    public static final String USERNAME_IDENTIFIER;

    /**
     * 跳转url
     */
    public static final String REDIRECT_URL_IDENTIFIER;

    /**
     * 密钥
     */
    public static final String PRIVATE_KEY;

    /**
     * 时效性（默认:30分钟）
     */
    public static final int LIMIT_MILLI_SECOND;

    /**
     * 默认的跳转主页
     */
    public static final String DEFAULT_REDIRECT_URL;


    static {
        Properties properties = new Properties();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
        if (is != null) {
            try {
                properties.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 注入默认值
        SOURCE_IDENTIFIER = properties.getProperty("sourceIdentifier", "source");
        KEY_IDENTIFIER = properties.getProperty("keyIdentifier", "key");
        USERNAME_IDENTIFIER = properties.getProperty("usernameIdentifier", "username");
        REDIRECT_URL_IDENTIFIER = properties.getProperty("redirectUrlIdentifier", "url");
        PRIVATE_KEY = properties.getProperty("privateKey", "FFCS_SSO");
        LIMIT_MILLI_SECOND = Integer
                .parseInt(properties.getProperty("limitMilliSecond", String.valueOf(30 * 60 * 1000)));
        DEFAULT_REDIRECT_URL = properties.getProperty("defaultRedirectUrl", "/index.jsp");
    }

}
