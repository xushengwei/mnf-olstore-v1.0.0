package com.mshd.olstore.mvp.model.entity;

import com.mshd.olstore.base.BaseResp;

/**
 * @author xushengwei
 * @date 2018/11/7
 */
public class RespGetToken extends BaseResp {
    private GetToken data ;

    public GetToken getData() {
        return data;
    }

    public void setData(GetToken data) {
        this.data = data;
    }

    public static class GetToken {
        private String accessid;
        private String policy;
        private String signature;
        private String dir;
        private String host;
        private String securitytoken;
        private String accesskeyid;
        private String accesskeysecret;
        private String Authorization;
        private String Signature;
        private String expire;

        public String getAccessid() {
            return accessid;
        }

        public void setAccessid(String accessid) {
            this.accessid = accessid;
        }

        public String getPolicy() {
            return policy;
        }

        public void setPolicy(String policy) {
            this.policy = policy;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getExpire() {
            return expire;
        }

        public void setExpire(String expire) {
            this.expire = expire;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getSecuritytoken() {
            return securitytoken;
        }

        public void setSecuritytoken(String securitytoken) {
            this.securitytoken = securitytoken;
        }

        public String getAccesskeyid() {
            return accesskeyid;
        }

        public void setAccesskeyid(String accesskeyid) {
            this.accesskeyid = accesskeyid;
        }

        public String getAccesskeysecret() {
            return accesskeysecret;
        }

        public void setAccesskeysecret(String accesskeysecret) {
            this.accesskeysecret = accesskeysecret;
        }

        public String getAuthorization() {
            return Authorization;
        }

        public void setAuthorization(String authorization) {
            Authorization = authorization;
        }
    }
}
