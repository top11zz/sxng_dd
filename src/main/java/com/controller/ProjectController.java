package com.controller;

import com.dingtalk.api.response.OapiUserGetResponse;
import com.entity.Progress;
import com.entity.Project;
import com.service.ProgressService;
import com.service.ProjectService;
import com.util.PaggingBean;
import com.util.ServiceResult;
import jdk.nashorn.internal.ir.RuntimeNode;
import jdk.nashorn.internal.runtime.Undefined;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
public class ProjectController {
    Logger log = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProgressService progressService;

    @RequestMapping("/test")
    public Project test(){
        return projectService.selectById(1);
    }

    @RequestMapping("/findProList")
    public ServiceResult findProList(Integer roleId,String userId,String tip,String name,HttpServletRequest request,Integer pageIndex,HttpSession session) throws Exception{
        log.info("findProList");
        log.info("userId-->"+userId);
        if(userId==null||userId.equals("")){
            ServiceResult serviceResult = ServiceResult.failure("1","缺少参数");
            return serviceResult;
        }
        boolean flag = false;
       // Integer roleId = (Integer) session.getAttribute("roleId");
        //String userId2 = (String) session.getAttribute("userId");
        log.info("RoleId-->"+roleId);
        //log.info("sessionUserId-->"+userId2);
        if(roleId!=null) {
                log.info("role-->"+roleId);
                if(roleId==472890535){
                    flag=true;
                }
        }
        Integer[] ids = null;

        if(flag){
            ids = projectService.findProByRole(tip,name);
        }else{
            ids = projectService.findProByUser(userId,tip,name);
        }
        //  Integer[] ids = projectService.findProByUser();
        PaggingBean paggingBean = new PaggingBean();
        if(pageIndex!=null){
            paggingBean.setPageIndex(pageIndex);
        }
        List<Project> projects = projectService.findProByIds(ids,paggingBean);
        if(projects!=null) {

            for (Project project : projects
            ) {
                List<Progress> progresses = progressService.findProgressByProId(project.getId());
                if (progresses.size() > 0) {
                    project.setStatus(progresses.get(0).getStatusText());
                }
            }
        }
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("data",projects);
        result.put("pageIndex",pageIndex);
        result.put("pageTotal",paggingBean.getTotalPage());
        ServiceResult serviceResult = ServiceResult.success(result);
        return serviceResult;
    }

    @RequestMapping("/findProById")
    public ServiceResult findProById(Integer proId)throws Exception{
        log.info("findProById");
        log.info("proId-->"+proId);
        if(proId==null){
            ServiceResult serviceResult = ServiceResult.failure("1","缺少参数");
            return serviceResult;
        }
        Project project = projectService.findProById(proId);
        List<Progress> progresses =  progressService.findProgressByProId(proId);
        if(progresses.size()>0){
            project.setStatus(progresses.get(0).getStatusText());
        }
        ServiceResult serviceResult = ServiceResult.success(project);
        return serviceResult;

    }

    @RequestMapping("/delPro")
    public ServiceResult delPro(String userId,Integer proId){
        log.info("delPro");
        log.info("userId-->"+userId);
        log.info("proId-->"+proId);
        if(userId==null||userId.equals("")||proId==null){
            ServiceResult  serviceResult = ServiceResult.failure("1","缺少参数");
            return serviceResult;
        }
        Integer[] ids = projectService.findProByUser2(userId);
        boolean flag = false;
        for (Integer id : ids) {
            if (id==proId){
                flag=true;
            }
        }
        if(flag){
            ServiceResult serviceResult = null;
            try {
                projectService.deleteProject(proId);
                serviceResult=ServiceResult.success(null);
            } catch (Exception e) {
                e.printStackTrace();
                serviceResult=ServiceResult.failure("3","删除失败");
            }
            return serviceResult;
        }
        return  ServiceResult.failure("2","无权限");
    }
    @RequestMapping("/editPro")
    public ServiceResult editPro(@RequestBody Project project){
        log.info("editPro");
        log.info("project-->"+project);
        log.info("userId-->"+project.getUserId());
        if(project.getUserId()==null||project.getUserId().equals("")||project.getId()==null){
            ServiceResult  serviceResult = ServiceResult.failure("1","缺少参数");
            return serviceResult;
        }
        Integer[] ids = projectService.findProByUser2(project.getUserId());
        boolean flag = false;
        for (Integer id : ids) {
            if (id==project.getId()){
                flag=true;
            }
        }
        if(flag){
            ServiceResult serviceResult = null;
            try {
                projectService.editProject(project);
                serviceResult=ServiceResult.success(null);
            } catch (Exception e) {
                e.printStackTrace();
                serviceResult=ServiceResult.failure("3","修改失败");
            }
            return serviceResult;
        }
        return  ServiceResult.failure("2","无权限");
    }

    @RequestMapping("/findAllProject")
    public ServiceResult findAllProject(){
        log.info("findAllProject");
        List<Project> projects = projectService.findAllProject();
        return ServiceResult.success(projects);
    }

    @RequestMapping("/findTips")
    public ServiceResult findTips(){
        log.info("findTips");
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        List<String> tipList = new ArrayList<String>();
        List<Project> proTips = projectService.findProTips();
        for (Project project: proTips
             ) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("tips",project.getTips());
            map.put("tipName",project.getTipName());
            list.add(map);
        }
        return ServiceResult.success(list);
    }

    @RequestMapping("/addProject")
    public ServiceResult addProject(@RequestBody Project project){
        log.info("addProject");
        log.info("project-->"+project);
        ServiceResult serviceResult = null;
        try {
            projectService.insProject(project);
            serviceResult = ServiceResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult = ServiceResult.failure("3","添加失败");
        }
        return serviceResult;
    }

}
