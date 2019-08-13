package cn.chenzw.sso.easy.client.support;


import cn.chenzw.sso.easy.client.support.entity.SSOEntity;
import cn.chenzw.sso.easy.core.constants.SSOConstants;
import cn.chenzw.sso.easy.core.exception.SSOException;
import cn.chenzw.toolkit.codec.AESUtils;
import cn.chenzw.toolkit.commons.UriExtUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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
        Map<String, String> paramMap = new HashMap<>();
        try {
            paramMap.put(SSOConstants.SOURCE_IDENTIFIER, source);
            paramMap.put(SSOConstants.USERNAME_IDENTIFIER, AESUtils.encryptAsHexString(userName, privateKey));
            paramMap.put(SSOConstants.KEY_IDENTIFIER, AESUtils.encryptAsHexString(userName + SSOConstants.KEY_SEPARATOR
                    + DateFormatUtils.format(new Date(), SSOConstants.KEY_TIMESTAMP_FORMAT), privateKey));
            paramMap.put(SSOConstants.REDIRECT_URL_IDENTIFIER,
                    URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException | NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new SSOException(e.getMessage());
        }
        return UriExtUtils.buildParams(serverSSOUrl, paramMap);
    }

    public static String generateSSOUrl(SSOEntity ssoEntity) {
        return generateSSOUrl(ssoEntity.getServerSSOUrl(), ssoEntity.getPrivateKey(), ssoEntity.getSource(),
                ssoEntity.getUserName(), ssoEntity.getRedirectUrl());
    }

    /**
     *  加密用戶名
     * @param userName
     * @param privateKey
     * @return
     */
    public static String getEncrypedUserName(String userName, String privateKey) {
        try {
            return AESUtils.encryptAsHexString(userName, privateKey);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new SSOException(e.getMessage());
        }
    }


    /**
     * 加密key
     * @param userName
     * @param privateKey
     * @return
     */
    public static String getEncrypedKey(String userName, String privateKey) {
        try {
            return AESUtils.encryptAsHexString(userName + SSOConstants.KEY_SEPARATOR + DateFormatUtils.format(new Date(), SSOConstants.KEY_TIMESTAMP_FORMAT), privateKey);
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            throw new SSOException(e.getMessage());
        }
    }

}
