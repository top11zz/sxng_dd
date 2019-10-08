package com.service;

import com.dao.*;
import com.entity.FirstLinkman;
import com.entity.Principal;
import com.entity.Project;
import com.util.PaggingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {
    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private PrincipalDao principalDao;

    @Autowired
    private FirstLinkmanDao firstLinkmanDao;

    @Autowired
    private ProgressDao ProgressDao;

    @Autowired
    private TailAfterDao tailAfterDao;

    public Project selectById(Integer id){
        return projectDao.selectById(id);
    }

    public Integer[] findProByUser(String userId,String tip,String name){
        return projectDao.findProByUser(userId,tip,name);
    }
    public Integer[] findProByRole(String tip,String name){
        return projectDao.findProByRole(tip,name);
    }

    public Integer[] findProByUser2(String userId){
        return projectDao.findProByUser2(userId);
    }

    public Integer[] findProjectIds(Map<String,Object> params){
        return projectDao.findProjectIds(params);
    }

    public List<Project> findProByIds(Integer[] ids, PaggingBean paggingBean){
        if(ids.length>0) {
            paggingBean.initPagging(projectDao.findProByIdsCount(ids));
            List<Project> projects = projectDao.findProByIds(ids,paggingBean.first(),paggingBean.last());
            for (Project project : projects) {
                List<FirstLinkman> firstLinkmen = firstLinkmanDao.findFirstLinkmenByProId(project.getId());
                List<Principal> principals = principalDao.findPrinByProId(project.getId());
                if (!firstLinkmen.isEmpty()) {
                    project.setFirstLinkmen(firstLinkmen);
                }
                if (!principals.isEmpty()) {
                    project.setPrincipals(principals);
                }
            }
            return projects;
        }else{
            return null;
        }

    }

    public Project findProById(Integer id){
        Project project = projectDao.findProById(id);
        List<FirstLinkman> firstLinkmen = firstLinkmanDao.findFirstLinkmenByProId(id);
        List<Principal> principals = principalDao.findPrinByProId(id);
        if(!firstLinkmen.isEmpty()){
            project.setFirstLinkmen(firstLinkmen);
        }
        if (!principals.isEmpty()){
            project.setPrincipals(principals);
        }
        return project;
    }
    @Transactional
    public void deleteProject(Integer proId) throws Exception{
        projectDao.deleteProject(proId);
        principalDao.delPrinByPro(proId);
        firstLinkmanDao.delLinkmanByPro(proId);
        ProgressDao.delByProId(proId);
        tailAfterDao.delByProId(proId);
    }

    @Transactional
    public void editProject(Project project) throws Exception{
        projectDao.editProject(project);
        principalDao.delPrinByPro(project.getId());
        principalDao.insPrinByPro(project.getPrincipals());
        firstLinkmanDao.delLinkmanByPro(project.getId());
        firstLinkmanDao.insLinkmanByPro(project.getFirstLinkmen());
    }
    @Transactional
    public void insProject(Project project) throws Exception{
        projectDao.insProject(project);
        System.out.println("proId-->"+project.getId());
        String number = getProNumber(project.getId());
        System.out.println("number-->"+number);
        projectDao.editProNumber(project.getId(),number);
        List<FirstLinkman> firstLinkmen = project.getFirstLinkmen();
        List<Principal> principals = project.getPrincipals();
        if(firstLinkmen!=null) {
            if(firstLinkmen.size()>0) {
                for (FirstLinkman firstLinkman : firstLinkmen) {
                    firstLinkman.setProjectId(project.getId());
                }
                firstLinkmanDao.insLinkmanByPro(firstLinkmen);
            }
        }
        for (Principal principal : principals) {
            principal.setProjectId(project.getId());
        }
        principalDao.insPrinByPro(principals);
    }
    public List<Project> findAllProject(){
        return projectDao.findAllProject();
    }

    public String getProNumber(Integer proId) throws Exception{
        String str = "TGEM-YW-";
        SimpleDateFormat sdf = new SimpleDateFormat("yy");
        Date date = new Date();
        str += sdf.format(date);
        str += String.format("%04d",proId);
        return str;
    }

    public List<Project> findProTips(){
        return projectDao.findProTips();
    }
}
