package com.util;

import com.config.Constant;
import com.config.URLConstant;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取access_token工具类
 */
public class AccessTokenUtil {
    private static final Logger bizLogger = LoggerFactory.getLogger(AccessTokenUtil.class);

    public static String getToken() throws RuntimeException {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_TOKKEN);
            OapiGettokenRequest request = new OapiGettokenRequest();

            request.setAppkey(Constant.APP_KEY);
            request.setAppsecret(Constant.APP_SECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            String accessToken = response.getAccessToken();
            return accessToken;
        } catch (ApiException e) {
            bizLogger.error("getAccessToken failed", e);
            throw new RuntimeException();
        }

    }

    public static void main(String[] args) throws ApiException {
        String accessToken = getToken();
        System.out.println(accessToken);
    }
}
