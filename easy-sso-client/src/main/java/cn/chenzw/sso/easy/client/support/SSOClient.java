package cn.chenzw.sso.easy.client.support;


import cn.chenzw.toolkit.codec.AESUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.omg.CORBA.portable.ApplicationException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


/**
 * SSO客户端
 *
 * @author chenzw
 */
public class SSOClient {

    /**
     * 生成SSO单点连接
     *
     * @param serverSSOUrl 单点服务器URL地址
     * @param privateKey   密钥
     * @param source       来源系统
     * @param userName     用户名
     * @param redirectUrl  登录成功后的跳转地址
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String generateSSOUrl(String serverSSOUrl, String privateKey, String source, String userName,
                                        String redirectUrl) {
        try {
            return String.format("%s?source=%s&username=%s&key=%s&url=%s", serverSSOUrl, source,
                    AESUtils.encryptAsHexString(userName, privateKey),
                    AESUtils.encryptAsHexString(userName + "##" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"), privateKey),
                    URLEncoder.encode(redirectUrl, "UTF-8"));
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getEncrypedUserName(String userName, String privateKey) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return AESUtils.encryptAsHexString(userName, privateKey);
    }

    public static String getEncrypedKey(String userName, String privateKey) throws ApplicationException {
        try {
            return AESUtils.encryptAsHexString(userName + "##" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"), privateKey);
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        String url = SSOClient
                .generateSSOUrl("http://localhost:8080/easy-sso-server-examples/itm-sso", "b51d5af6c801ff4bfd31a4a154f35d35", "my",
                        "admin", "/index.jsp");
        System.out.println(url);

        Date date = new Date(1565274749000L);

        String format = DateFormatUtils.format(date, "yyyy-MM-dd hh:mm:ss");
        System.out.println(format);
    }
}
