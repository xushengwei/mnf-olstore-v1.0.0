package com.mshd.olstore.mvp.model.entity;


import com.mshd.olstore.base.BaseResp;

/**
 * @author xushengwei
 * @date 2018/11/7
 */
public class RespLogin extends BaseResp {

    private LoginBean data ;

    public LoginBean getData() {
        return data;
    }

    public void setData(LoginBean data) {
        this.data = data;
    }

    public static class LoginBean {
        private String sessionId ;
        private String userId ;
        private String identity ;

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
