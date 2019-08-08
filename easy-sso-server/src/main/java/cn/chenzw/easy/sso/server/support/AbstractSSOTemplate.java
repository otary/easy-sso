package cn.chenzw.easy.sso.server.support;


import cn.itm.ffcs.sso.server.constants.SSOConstants;
import cn.itm.ffcs.sso.server.entify.SSODefinition;
import cn.itm.ffcs.sso.server.exception.SSOException;
import cn.itm.ffcs.sso.server.utils.HttpHolder;
import com.bsnnms.bean.common.AESSecurity;
import com.bsnnms.bean.systemLog.bo.StaffLog;
import com.bsnnms.bean.user.SessionMgr_WorkFlow;
import com.bsnnms.bean.user.StaffInfo;
import com.bsnnms.bean.user.XJUserSecurity;
import com.bsnnms.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
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
    protected abstract void preHandler(SSODefinition ssoDefinition) throws Exception;

    /**
     * 校验key是否正确
     * @param ssoDefinition
     * @return
     */
    private boolean checkKey(SSODefinition ssoDefinition) {
        String plainKey = null;
        try {
            plainKey = AESSecurity.decode(ssoDefinition.getKey(), ssoDefinition.getSourcePrivateKey());
        } catch (ApplicationException e) {
            throw new SSOException("key解析失败!");
        }
        String[] aPlainKey = StringUtils.split(plainKey, "##");
        if (aPlainKey != null && aPlainKey.length == 2) {
            // key中用户名不正确
            if (!ssoDefinition.getPlainUserName().equals(aPlainKey[0])) {
                throw new SSOException("key密钥校验失败!");
            }

            Date keyDate = null;
            try {
                keyDate = DateUtils.parseDate(aPlainKey[1], "yyyyMMddHHmmss");
            } catch (ParseException e) {
                throw new SSOException("key密钥无效!");
            }
            Date today = Calendar.getInstance().getTime();
            if (Math.abs(keyDate.getTime() - today.getTime()) >= SSOConstants.LIMIT_MILLI_SECOND) {
                throw new SSOException("key密钥已过期! timestamp:[" + keyDate.getTime() + "]");
            }
        } else {
            throw new SSOException("无效的key密钥!");
        }
        return true;
    }

    /**
     * 校验是否已登录
     * @param ssoDefinition
     * @return
     */
    private boolean checkLoginedIn(SSODefinition ssoDefinition) {
        HttpSession session = ssoDefinition.getRequest().getSession();
        StaffInfo info = (StaffInfo) session.getAttribute("staffInfo");
        if (info != null && info.getLoginName().equals(ssoDefinition.getPlainUserName())) {
            return true;
        }
        return false;
    }

    /**
     * 数据校验
     * @return
     */
    protected abstract boolean validate(SSODefinition ssoDefinition);

    /**
     * 用户登录
     */
    protected void login(SSODefinition ssoDefinition) {
        StaffInfo staffInfo = null;
        try {
            HttpServletRequest request = ssoDefinition.getRequest();
            HttpSession session = request.getSession();
            staffInfo = XJUserSecurity.check(ssoDefinition.getPlainUserName(), "");
            staffInfo.setLoginId(session.getId());
            staffInfo.setLoginIp(request.getRemoteAddr());
            session.setAttribute("staffInfo", staffInfo);
            SessionMgr_WorkFlow.logIn(session, staffInfo.getStaffId());

            // 填写登陆日志
            StaffLog.addLoginLog(staffInfo, session);
        } catch (com.bsnnms.exception.SystemException e) {
            throw new SSOException("SSO登录失败! 详细信息: [" + e.getMessage() + "]");
        } catch (ApplicationException e) {
            throw new SSOException("SSO登录失败! 详细信息: [" + e.getMessage() + "]");
        }
    }


    /**
     * 后置处理器
     */
    protected abstract void postHandler(SSODefinition ssoDefinition) throws Exception;


    /**
     * 默认的后置处理器
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

    public void dispach() throws Exception {
        SSODefinition ssoDefinition = new SSODefinition(HttpHolder.REQUEST.get(), HttpHolder.RESPONSE.get());
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
