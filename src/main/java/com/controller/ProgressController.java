package com.controller;

import com.entity.Progress;
import com.service.ProgressService;
import com.util.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProgressController {
    Logger log = LoggerFactory.getLogger(ProgressController.class);

    @Autowired
    private ProgressService progressService;

    @RequestMapping("/findProgressList")
    public ServiceResult findProgressList(Integer proId){
        log.info("findProgressList");
        log.info("proId-->"+proId);
        ServiceResult serviceResult = null;
        if(proId!=null){
        Map<String,Object> result = new HashMap<String, Object>();
        serviceResult=ServiceResult.success(progressService.findProgressByProId(proId));
        return serviceResult;
        }else{
            serviceResult = ServiceResult.failure("1","缺少参数");
            return serviceResult;
        }
    }

    @RequestMapping("/findProgressById")
    public ServiceResult findProgressById(Integer id){
        ServiceResult  serviceResult=ServiceResult.success(progressService.findProgressById(id));
        return serviceResult;
    }
    @RequestMapping("/delProgress")
    public ServiceResult delProgress(Integer progId,String userId){
        log.info("delProgress");
        log.info("progId-->"+progId);
        log.info("userId-->"+userId);

        if(userId==null||userId.equals("")||progId==null){
            log.info("Message-->缺少参数");
            ServiceResult serviceResult = ServiceResult.failure("1","缺少参数1");
            return serviceResult;
        }else {
            if (check(progId, userId)) {
                ServiceResult serviceResult = null;
                try {
                    log.info("Message-->success");
                    progressService.deleteProgress(progId);
                    serviceResult = ServiceResult.success("success");
                    log.info("serviceResultMSG--->"+serviceResult.getMessage());
                    log.info("serviceResultCODE--->"+serviceResult.getCode());
                } catch (Exception e) {
                    e.printStackTrace();
                    serviceResult = ServiceResult.failure("3", "删除失败");
                }
                    log.info("serviceResultMSG--->"+serviceResult.getMessage());
                    log.info("serviceResultCODE--->"+serviceResult.getCode());
                return serviceResult;
            }else {
                return ServiceResult.failure("2", "无权限");
            }
        }
    }

    @RequestMapping("/editProgress")
    public ServiceResult editProgress(@RequestBody Progress progress){
        log.info("editProgress");
        log.info("progress-->"+progress);
        log.info("userId-->"+progress.getUserId());
        ServiceResult serviceResult = null;
        if(progress.getUserId()==null||progress.getUserId().equals("")||progress.getId()==null){
            serviceResult = ServiceResult.failure("1","缺少参数");
            return serviceResult;
        }
        if(check(progress.getId(),progress.getUserId())){
            try {
                progressService.editProgress(progress);
                serviceResult=ServiceResult.success(null);
            } catch (Exception e) {
                e.printStackTrace();
                serviceResult=ServiceResult.failure("3","修改失败");
            }
            return serviceResult;
        }
        return ServiceResult.failure("2","无权限");
    }
    @RequestMapping("/editProgressTest")
    public ServiceResult editProgressTest(@RequestBody Progress progress) throws Exception{
        log.info("editProgressTest");
        log.info("progress-->"+progress);
        System.out.println(progress);
        progressService.editProgressTest(progress);
        return ServiceResult.success(null);
    }

    @RequestMapping("/addProgress")
    public ServiceResult addProgress(@RequestBody Progress progress){
        log.info("addProgress");
        log.info("progress-->"+progress);
        ServiceResult serviceResult = null;
        try {
            progressService.insProgress(progress);
            serviceResult = ServiceResult.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult = ServiceResult.failure("3","添加失败");
        }
        return serviceResult;
    }


    //判断用户是否属于该进展
    public boolean check(Integer progId,String userId){
        boolean flag = false;
        Progress progress = progressService.findProgressById(progId);
        if(progress.getAfterUserIds()==null||progress.getPrinUserIds()==null){
            return flag;
        }
        String[] ids1 = progress.getAfterUserIds().split(",");
        String[] ids2 = progress.getPrinUserIds().split(",");
        for (int i = 0 ; i < ids1.length;i++){
            if (userId.equals(ids1[i])){
                flag = true;
                break;
            }
        }
        for(int j = 0 ; j < ids2.length;j++){
            if (userId.equals(ids2[j])){
                flag = true;
                break;
            }
        }
        return flag;

    }
}
