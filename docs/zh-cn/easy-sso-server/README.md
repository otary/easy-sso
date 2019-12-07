# easy-sso-server

sso服务端


## Usage

### 添加依赖
 
- maven 方式
 
 ``` xml
<dependency>
    <groupId>cn.chenzw.sso</groupId>
    <artifactId>easy-sso-server</artifactId>
    <version>1.0.0</version>
</dependency>
```

- gradle 方式

``` 
compile group: 'cn.chenzw.sso', name: 'easy-sso-server', version: '1.0.0'

```

### 开启SSO

- 使用`@EnableSSO`开启SSO服务

``` java
@EnableSSO
@Configuration
public class SSOConfig {

}
```

### 创建默认单点处理类

- 继承`cn.chenzw.sso.easy.server.support.AbstractSSOTemplate`类，实现`checkLoginedIn()`、`login()`方法
  - 可选择重写`preHandler()`、`validate()`、`postHandler()`等方法来实现更多的自定义功能
- 添加`@OSS`注解，用于标识此类为单点处理类
  -  source属性：指定此类处理哪些系统（*号表示处理所有系统的单点请求，也就是默认单点处理器，有且仅能有一个默认单点处理类）

示例：

``` java
@Component
@SSO(source = "*")
public class DefaultSSOTemplate extends AbstractSSOTemplate {

    /**
     *  判断是否已登录
     **/
    @Override
    protected boolean checkLoginedIn(SSODefinition ssoDefinition) {
        // 根据实际情况编写
        HttpSession session = ssoDefinition.getRequest().getSession();
        StaffInfo info = (StaffInfo) session.getAttribute("staffInfo");
        if (info != null && info.getLoginName().equals(ssoDefinition.getPlainUserName())) {
            return true;
        }
        return false;
    }

    @Override
    protected void login(SSODefinition ssoDefinition) {

        try {
            HttpServletRequest request = ssoDefinition.getRequest();
            HttpSession session = request.getSession();

            // 根据实际情况，编写单点成功后的处理逻辑（如，写入日志、写入session等）
        }  catch (Exception e) {
            throw new SSOException("SSO登录失败! 详细信息: [" + e.getMessage() + "]");
        }
    }
}
```

### 配置单点参数

- 可在项目`resources`下新建`sso.properties` 或 直接在`application.properties`中添加单点配置参数

``` properties
## 指定单点服务端私钥(必须)
sso.private-key=123456

## 以下配置可选

## 指定单点开放的url地址
sso.entrance-uri=/easy-sso
## 指定系统来源的url参数名称
sso.source-identifier=source
## 指定加密数据的url参数名称
sso.key-identifier=key
## 指定单点用户名的url参数名称
sso.username-identifier=username
## 指定单点成功后跳转的url参数名称
sso.redirect-url-identifier=redirectUrl
## 指定单点链接的时效（默认:30分钟）
sso.limit-milli-second=1800000
## 指定默认单点成功后的跳转地址(当未指定redirectUrl参数时)
sso.default-redirect-url=/
```

**举例**：

配置修改如下：

``` properties
sso.entrance-uri=/my-sso
sso.source-identifier=source2
sso.key-identifier=key2
sso.username-identifier=username2
sso.redirect-url-identifier=redirectUrl2
```

则单点链接格式变为：`http://localhost:8080/my-sso?source2=xxx&key2=xxx&username2=xxx&redirectUrl2=xxx`

---

到此，sso服务端配置完成，可以访问:`http://localhost:8080/easy-sso`测试是否配置开启成功（弹出提示: source参数为空! 则配置成功）


