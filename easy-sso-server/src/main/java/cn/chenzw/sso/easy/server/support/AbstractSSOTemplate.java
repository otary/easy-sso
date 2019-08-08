package cn.chenzw.sso.easy.server.support;


import cn.chenzw.sso.easy.server.constants.SSOConstants;
import cn.chenzw.sso.easy.server.entity.SSODefinition;
import cn.chenzw.sso.easy.server.exception.SSOException;
import cn.chenzw.toolkit.codec.AESUtils;
import cn.chenzw.toolkit.http.HttpHolder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author chenzw
 */
public abstract class AbstractSSOTemplate {

    /**
     * 前置处理器
     */
    protected void preHandler(SSODefinition ssoDefinition) throws Exception {

    }

    /**
     * 校验key是否正确
     *
     * @param ssoDefinition
     * @return
     */
    private boolean checkKey(SSODefinition ssoDefinition) {
        String plainKey = getPlainKey(ssoDefinition.getKey(), ssoDefinition.getSourcePrivateKey());

        String[] aPlainKey = StringUtils.split(plainKey, SSOConstants.KEY_SEPARATOR);
        if (aPlainKey != null && aPlainKey.length == 2) {
            String userNameKey = aPlainKey[0];
            String timestampKey = aPlainKey[1];
            // key中用户名不正确
            if (!ssoDefinition.getPlainUserName().equals(userNameKey)) {
                throw new SSOException("key密钥校验失败!");
            }
            doCheckKeyExpired(timestampKey);
        } else {
            throw new SSOException("无效的key密钥!");
        }
        return true;
    }

    private String getPlainKey(String key, String sourcePrivateKey) {
        try {
            return new String(AESUtils.decryptHexString(key, sourcePrivateKey), StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | DecoderException e) {
            throw new SSOException("key解析失败!");
        }
    }

    private Date getKeyTimestamp(String keyTimestamp) {
        try {
            return DateUtils.parseDate(keyTimestamp, SSOConstants.KEY_TIMESTAMP_FORMAT);
        } catch (ParseException e) {
            throw new SSOException("key密钥无效!");
        }
    }

    private void doCheckKeyExpired(String timestampKey) {
        Date keyDate = getKeyTimestamp(timestampKey);
        Date today = Calendar.getInstance().getTime();
        if (Math.abs(keyDate.getTime() - today.getTime()) >= SSOConstants.LIMIT_MILLI_SECOND) {
            throw new SSOException("key密钥已过期! timestamp:[" + keyDate.getTime() + "]");
        }
    }

    /**
     * 校验是否已登录
     *
     * @param ssoDefinition
     * @return
     */
    protected abstract boolean checkLoginedIn(SSODefinition ssoDefinition);

    /**
     * 用户登录
     */
    protected abstract void login(SSODefinition ssoDefinition);

    /**
     * 数据校验
     *
     * @return
     */
    protected boolean validate(SSODefinition ssoDefinition) {
        return true;
    }


    /**
     * 后置处理器
     */
    protected void postHandler(SSODefinition ssoDefinition) throws Exception {

    }

    /**
     * 默认的后置处理器
     *
     * @Description 根据url参数进行跳转，否则跳转到默认主页
     */
    private void defaultPostHandler(SSODefinition ssoDefinition) {
        try {
            if (!StringUtils.isBlank(ssoDefinition.getRedirectUrl())) {
                ssoDefinition.getResponse().sendRedirect(URLDecoder.decode(ssoDefinition.getRedirectUrl(), "UTF-8"));
            } else {
                ssoDefinition.getResponse().sendRedirect(SSOConstants.DEFAULT_REDIRECT_URL);
            }
        } catch (IOException e) {
            throw new SSOException("单点跳转失败!");
        }
    }

    /**
     * 处理流程
     *
     * @throws Exception
     */
    public void dispach() throws Exception {
        SSODefinition ssoDefinition = new SSODefinition(HttpHolder.getRequest(), HttpHolder.getResponse());
        this.preHandler(ssoDefinition);
        if (checkKey(ssoDefinition) && validate(ssoDefinition)) {
            if (!checkLoginedIn(ssoDefinition)) {
                this.login(ssoDefinition);
            }
            this.postHandler(ssoDefinition);
            this.defaultPostHandler(ssoDefinition);
        }
    }


}
