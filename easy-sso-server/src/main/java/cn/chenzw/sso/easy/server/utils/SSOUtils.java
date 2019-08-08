package cn.chenzw.sso.easy.server.utils;

import cn.chenzw.sso.easy.server.constants.SSOConstants;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.DigestUtils;

/**
 * @author chenzw
 */
public final class SSOUtils {

    private SSOUtils() {
    }

    /**
     * 生成系统密钥
     *
     * @param source
     * @return
     */
    public static String getSourcePrivateKey(String source) {
        return DigestUtils.md5DigestAsHex((source + SSOConstants.PRIVATE_KEY).getBytes());
    }

    /**
     * 生成错误消息块
     *
     * @param title
     * @param msg
     * @return
     */
    public static String buildHtmlMsg(String title, String msg) {
        return "<div style=\"position: relative; width: 100%; height: 500px; text-align:center;\">"
                + "<div style=\" width: 300px; margin: 100px auto; padding: 10px; border: 1px solid #ccc; border-radius: 5px; overflow: hidden;\">"
                + "<div style=\"padding: 10px 3px; border-bottom: 1px solid #ccc; font-weight: bold;\">" + title
                + "</div>" + "<div style=\"padding: 10px 3px;\">" + "【错误提示】: " + msg + "</div></div></div>";
    }

    /**
     * 获取数组参数的第一个值
     *
     * @param params
     * @return
     */
    public static String getFirstParamter(Object params) {
        if (params == null || ArrayUtils.isEmpty((String[]) params)) {
            return "";
        }
        return ((String[]) params)[0];
    }

}
