package cn.chenzw.sso.easy.server.examples.template;

import cn.chenzw.sso.easy.server.ext.SSO;
import org.springframework.stereotype.Component;

@Component
@SSO(source = "my")
public class MySSOTemplate extends DefaultSSOTemplate {
}
