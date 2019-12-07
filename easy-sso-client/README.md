# easy-sso-client

sso客户端，用于快速生成单点链接

## Usage

### 添加依赖

- **maven方式**
 
 ``` xml
<dependency>
    <groupId>cn.chenzw.sso</groupId>
    <artifactId>easy-sso-client</artifactId>
    <version>1.0.0</version>
</dependency>

```

- **gradle方式**

```
compile group: 'cn.chenzw.sso', name: 'easy-sso-client', version: '1.0.0'
```


### 调用方法

``` java
/**
  * 生成SSO单点链接
  * @param serverSSOUrl 参数1：单点认证地址
  * @param privateKey 参数2：密钥（由SSO服务端提供）
  * @param source 参数3：系统标识符
  * @param userName 参数4：单点用户名
  * @param redirectUrl 参数5：单点成功后跳转地址
  * @return
  * @throws UnsupportedEncodingException
  **/
String ssoUrl = SSOClient.generateSSOUrl("http://localhost:8080/easy-sso", "b51d5af6c801ff4bfd31a4a154f35d35", "crm", "admin", "/index.jsp");
```

**附录：单点链接参数说明**
- **source**: 系统标识符
- **privateKey**：密钥（由SSO服务端提供）
- **userName**：单点用户名
- **url**：单点成功后跳转的URL地址
