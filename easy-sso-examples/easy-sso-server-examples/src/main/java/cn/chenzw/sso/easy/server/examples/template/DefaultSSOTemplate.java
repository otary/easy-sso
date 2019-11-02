package cn.chenzw.sso.easy.server.examples.template;

import cn.chenzw.sso.easy.server.entity.SSODefinition;
import cn.chenzw.sso.easy.server.ext.SSO;
import cn.chenzw.sso.easy.server.support.AbstractSSOTemplate;
import org.springframework.stereotype.Component;

@Component
@SSO(source = "*")
public class DefaultSSOTemplate extends AbstractSSOTemplate {
    @Override
    protected boolean checkLoginedIn(SSODefinition ssoDefinition) {
        return false;
    }

    @Override
    protected void login(SSODefinition ssoDefinition) {
        System.out.println("-----------------登录！");
    }
}
