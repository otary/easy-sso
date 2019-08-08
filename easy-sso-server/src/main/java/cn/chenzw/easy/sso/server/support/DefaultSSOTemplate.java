package cn.chenzw.easy.sso.server.support;


import cn.itm.ffcs.sso.server.entify.SSODefinition;

/**
 * 默认单点登录处理类
 * @author chenzw
 */
public class DefaultSSOTemplate extends AbstractSSOTemplate {


    @Override
    protected void preHandler(SSODefinition ssoDefinition) throws Exception {

    }

    @Override
    protected boolean validate(SSODefinition ssoDefinition) {
        return true;
    }

    @Override
    protected void postHandler(SSODefinition ssoDefinition) throws Exception {

    }
}
