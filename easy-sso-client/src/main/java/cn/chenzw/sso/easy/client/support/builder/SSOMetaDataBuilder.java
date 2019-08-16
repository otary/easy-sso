package cn.chenzw.sso.easy.client.support.builder;


import cn.chenzw.sso.easy.core.constants.SSOConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author chenzw
 */
public class SSOMetaDataBuilder {

    private String sourceIdentifier;

    private String keyIdentifier;

    private String usernameIdentifier;

    private String redirectUrlIdentifier;

    private int limitMilliSecond;


    public static SSOMetaDataBuilder create() {
        return new SSOMetaDataBuilder();
    }

    public SSOMetaDataBuilder setSourceIdentifier(String sourceIdentifier) {
        this.sourceIdentifier = sourceIdentifier;
        return this;
    }

    public SSOMetaDataBuilder setKeyIdentifier(String keyIdentifier) {
        this.keyIdentifier = keyIdentifier;
        return this;
    }

    public SSOMetaDataBuilder setUsernameIdentifier(String usernameIdentifier) {
        this.usernameIdentifier = usernameIdentifier;
        return this;
    }

    public SSOMetaDataBuilder setRedirectUrlIdentifier(String redirectUrlIdentifier) {
        this.redirectUrlIdentifier = redirectUrlIdentifier;
        return this;
    }

    public SSOMetaDataBuilder setLimitMilliSecond(int limitMilliSecond) {
        this.limitMilliSecond = limitMilliSecond;
        return this;
    }

    public SSOMetaData build() {
        return new SSOMetaData(StringUtils.defaultIfBlank(sourceIdentifier, SSOConstants.SOURCE_IDENTIFIER),
                StringUtils.defaultIfBlank(keyIdentifier, SSOConstants.KEY_IDENTIFIER),
                StringUtils.defaultIfBlank(usernameIdentifier, SSOConstants.USERNAME_IDENTIFIER),
                StringUtils.defaultIfBlank(redirectUrlIdentifier, SSOConstants.REDIRECT_URL_IDENTIFIER),
                ObjectUtils.defaultIfNull(limitMilliSecond, SSOConstants.LIMIT_MILLI_SECOND));
    }

    public static class SSOMetaData implements Serializable {

        private String sourceIdentifier;

        private String keyIdentifier;

        private String usernameIdentifier;

        private String redirectUrlIdentifier;

        private Integer limitMilliSecond;

        public SSOMetaData() {
        }

        public SSOMetaData(String sourceIdentifier, String keyIdentifier, String usernameIdentifier,
                String redirectUrlIdentifier, Integer limitMilliSecond) {
            this.sourceIdentifier = sourceIdentifier;
            this.keyIdentifier = keyIdentifier;
            this.usernameIdentifier = usernameIdentifier;
            this.redirectUrlIdentifier = redirectUrlIdentifier;
            this.limitMilliSecond = limitMilliSecond;
        }

        public String getSourceIdentifier() {
            return sourceIdentifier;
        }

        public void setSourceIdentifier(String sourceIdentifier) {
            this.sourceIdentifier = sourceIdentifier;
        }

        public String getKeyIdentifier() {
            return keyIdentifier;
        }

        public void setKeyIdentifier(String keyIdentifier) {
            this.keyIdentifier = keyIdentifier;
        }

        public String getUsernameIdentifier() {
            return usernameIdentifier;
        }

        public void setUsernameIdentifier(String usernameIdentifier) {
            this.usernameIdentifier = usernameIdentifier;
        }

        public String getRedirectUrlIdentifier() {
            return redirectUrlIdentifier;
        }

        public void setRedirectUrlIdentifier(String redirectUrlIdentifier) {
            this.redirectUrlIdentifier = redirectUrlIdentifier;
        }

        public Integer getLimitMilliSecond() {
            return limitMilliSecond;
        }

        public void setLimitMilliSecond(Integer limitMilliSecond) {
            this.limitMilliSecond = limitMilliSecond;
        }
    }
}
