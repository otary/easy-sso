package cn.chenzw.easy.sso.server.support;

import cn.itm.ffcs.sso.server.utils.SSOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Console;

/**
 * 系统密钥生成器
 * @author chenzw
 */
public class SourceKeyGenerator {

    public static void main(String[] args) {
        String source = "";
        if (ArrayUtils.isEmpty(args)) {
            Console console = System.console();
            if (console == null) {
                throw new IllegalStateException("不能使用控制台");
            }
            while (StringUtils.isBlank(source)) {
                source = console.readLine("请输入系统(source)标识符：");
            }
        }

        System.out.println("系统:" + source);
        System.out.println("密钥:" + SSOUtils.getSourcePrivateKey(source));
    }
}
