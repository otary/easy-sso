package cn.chenzw.sso.easy.keygen;

import cn.chenzw.sso.easy.core.utils.SSOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Console;

/**
 * 系统密钥生成器
 *
 * @author chenzw
 */
public class SourceKeyGenerator {

    public static void main(String[] args) {
        String source = "", privateKey = "";

        if (ArrayUtils.isEmpty(args)) {
            Console console = System.console();
            if (console == null) {
                throw new IllegalStateException("不能使用控制台");
            }
            while (StringUtils.isBlank(source) || StringUtils.isBlank(privateKey)) {
                privateKey = console.readLine("请输入私钥: ");
                source = console.readLine("请输入系统(source)标识符：");
            }
        }

        System.out.println("系统:" + source);
        System.out.println("密钥:" + SSOUtils.getSourcePrivateKey(source, privateKey));
    }
}
