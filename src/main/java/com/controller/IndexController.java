package com.controller;

import com.config.Constant;
import com.config.URLConstant;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.entity.MsgForm;
import com.taobao.api.ApiException;
import com.util.AccessTokenUtil;
import com.util.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业内部E应用Quick-Start示例代码 实现了最简单的免密登录（免登）功能
 */
@RestController
public class IndexController {
    private static final Logger bizLogger = LoggerFactory.getLogger(IndexController.class);

    /**
     * 欢迎页面,通过url访问，判断后端服务是否启动
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }

    /**
     * 钉钉用户登录，显示当前登录用户的userId和名称
     *
     * @param requestAuthCode 免登临时code
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ServiceResult login(@RequestParam(value = "authCode") String requestAuthCode,HttpServletRequest requests) {
        HttpSession session = requests.getSession();
        bizLogger.info("login");
        bizLogger.info("requestAuthCode/authCode-->"+requestAuthCode);
        //获取accessToken,注意正是代码要有异常流处理
        String accessToken = AccessTokenUtil.getToken();

        //获取用户信息
        DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_USER_INFO);
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(requestAuthCode);
        request.setHttpMethod("GET");

        OapiUserGetuserinfoResponse response;
        try {
            response = client.execute(request, accessToken);
            bizLogger.info("response-->"+response);
            bizLogger.info("userId-->"+response.getUserid());
            bizLogger.info("userName-->"+getUserName(accessToken, response.getUserid()));
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
        //3.查询得到当前用户的userId
        // 获得到userId之后应用应该处理应用自身的登录会话管理（session）,避免后续的业务交互（前端到应用服务端）每次都要重新获取用户身份，提升用户体验
        String userId = response.getUserid();
        String userName = getUserName(accessToken, userId);
        //
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userId", userId);
        resultMap.put("userName", userName);
        resultMap.put("accessToken",accessToken);
        resultMap.put("roleId",null);
        OapiUserGetResponse responses = getUser(accessToken,userId);
        List<OapiUserGetResponse.Roles> roles = responses.getRoles();
        if(roles!=null) {
            if(roles.size()>0) {
                for (OapiUserGetResponse.Roles role : roles) {
                    bizLogger.info("roles-->" + role.getId());
                    if (role.getId() == 472890535) {
                        bizLogger.info("roles2-->" + role.getId());
                        session.setAttribute("roleId", 472890535);
                        resultMap.put("roleId",role.getId());
                        break;
                    }
                    resultMap.put("roleId",role.getId());
                }
            }
        }
        session.setAttribute("userId",userId);
        ServiceResult serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
    }

    /**
     * 获取用户姓名
     *
     * @param accessToken
     * @param userId
     * @return
     */
    private String getUserName(String accessToken, String userId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_USER_GET);
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid(userId);
            request.setHttpMethod("GET");
            OapiUserGetResponse response = client.execute(request, accessToken);
            return response.getName();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取用户详情
     * @param accessToken
     * @param userId
     * @return
     */
    @RequestMapping("/getUser")
    public OapiUserGetResponse getUser(String accessToken, String userId){
        bizLogger.info("getUser");
        bizLogger.info("accessToken-->"+accessToken);
        bizLogger.info("userId-->"+userId);
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_USER_GET);
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid(userId);
            request.setHttpMethod("GET");
            OapiUserGetResponse response = client.execute(request, accessToken);
            return response;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取部门列表
     * @param accessToken
     * @param fetchChild（是否递归）
     * @param id（父部门id）
     * @return
     */
    @RequestMapping("/getDepartmentList")
    public OapiDepartmentListResponse getDepartmentList(String accessToken,Boolean fetchChild,String id){
        bizLogger.info("getDepartmentList");
        bizLogger.info("accessToken-->"+accessToken);
        bizLogger.info("fetchChild-->"+fetchChild);
        bizLogger.info("id-->"+id);
        try{
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
            OapiDepartmentListRequest request = new OapiDepartmentListRequest();
            request.setId(id);
            request.setFetchChild(fetchChild);
            request.setHttpMethod("GET");
            OapiDepartmentListResponse response = client.execute(request, accessToken);
            return response;
        }catch (ApiException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据部门id获取用户信息
     * @param accessToken
     * @param departmentId
     * @param offset
     * @param size
     * @return
     */
    @RequestMapping("/getUsersByDepartment")
    public OapiUserSimplelistResponse getUsersByDepartment(String accessToken,Long departmentId,Long offset,Long size){
        bizLogger.info("getUsersByDepartment");
        bizLogger.info("accessToken-->"+accessToken);
        bizLogger.info("departmentId-->"+departmentId);
        bizLogger.info("offset-->"+offset);
        bizLogger.info("size-->"+size);
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
            OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
            if(offset==null||offset < 0){
                request.setOffset(0L);
            }else{
                request.setOffset(offset);
            }
            if(size==null){
                request.setOffset(10L);
            }else{
                request.setSize(size);
            }

            request.setDepartmentId(departmentId);

            request.setHttpMethod("GET");
            OapiUserSimplelistResponse response = client.execute(request, accessToken);
            return response;
        }catch (ApiException e){
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping("/pushMsg")
    public OapiMessageCorpconversationAsyncsendV2Response pushMsg(@RequestBody MsgForm msgForm) throws ApiException {
        System.out.println(msgForm.getMsg().getMsgtype());
        System.out.println(msgForm.getAccessToken());
        System.out.println(msgForm.getMsg().getActionCard());
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        String userIdList = "";
        bizLogger.info("pushMsg");
        List<OapiRoleSimplelistResponse.OpenEmpSimple> userList = getUserIdByRoles(msgForm.getAccessToken());
        if(userList!=null){
            for (OapiRoleSimplelistResponse.OpenEmpSimple list:userList){
                bizLogger.info("userId-->"+list.getUserid());
                userIdList += list.getUserid() + ",";
            }
        }
        if(userIdList!=null){
            userIdList = userIdList.substring(0,userIdList.length()-1);
        }
        bizLogger.info("userIdList-->"+userIdList);
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList(userIdList);
        request.setAgentId(Constant.AGENT_ID);
        request.setToAllUser(false);
        request.setMsg(msgForm.getMsg());
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, msgForm.getAccessToken());
        return response;
    }

    public List<OapiRoleSimplelistResponse.OpenEmpSimple> getUserIdByRoles(String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/role/simplelist");
        OapiRoleSimplelistRequest request = new OapiRoleSimplelistRequest();
        request.setRoleId(472890535L);
        request.setOffset(0L);
        request.setSize(10L);
        OapiRoleSimplelistResponse response = client.execute(request, accessToken);
        bizLogger.info("getUserIdByRoles-->"+response.getResult());
        if(response.getResult()==null){
            return null;
        }
        return response.getResult().getList();
    }
}


