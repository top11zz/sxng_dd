package com.entity;

import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;

public class MsgForm {
    private OapiMessageCorpconversationAsyncsendV2Request.Msg msg;

    private String accessToken;

    public OapiMessageCorpconversationAsyncsendV2Request.Msg getMsg() {
        return msg;
    }

    public void setMsg(OapiMessageCorpconversationAsyncsendV2Request.Msg msg) {
        this.msg = msg;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "MsgForm{" +
                "msg=" + msg +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
