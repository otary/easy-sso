package cn.chenzw.sso.easy.server.ext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于扩展单点处理逻辑
 * @author chenzw
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SSO {

    /**
     * 来源系统
     * @return
     */
    String[] source();
}
