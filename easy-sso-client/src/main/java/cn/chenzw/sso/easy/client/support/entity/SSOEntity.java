package cn.chenzw.sso.easy.client.support.entity;

public class SSOEntity {

    private String serverSSOUrl;
    private String privateKey;
    private String source;
    private String userName;
    private String redirectUrl;

    public String getServerSSOUrl() {
        return serverSSOUrl;
    }

    public void setServerSSOUrl(String serverSSOUrl) {
        this.serverSSOUrl = serverSSOUrl;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public String toString() {
        return "SSOEntity{" + "serverSSOUrl='" + serverSSOUrl + '\'' + ", privateKey='" + privateKey + '\''
                + ", source='" + source + '\'' + ", userName='" + userName + '\'' + ", redirectUrl='" + redirectUrl
                + '\'' + '}';
    }
}
