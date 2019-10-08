package com.service;

import com.alibaba.fastjson.serializer.AfterFilter;
import com.dao.ProgressDao;
import com.dao.TailAfterDao;
import com.entity.Progress;
import com.entity.TailAfter;
import com.util.PaggingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProgressService {
    @Autowired
    private ProgressDao progressDao;

    @Autowired
    private TailAfterDao tailAfterDao;

    public List<Progress> findProgressByProId(Integer proId){
        List<Progress> progresses =  progressDao.findProgressByProId(proId);
        for (Progress progress: progresses
        ) {
            progress.setTailAfters(tailAfterDao.findAfterByProgId(progress.getId()));
        }
        return progresses;
    }



    @Transactional
    public void deleteProgress(Integer progId) throws Exception{
        progressDao.delById(progId);
        tailAfterDao.delByProgressId(progId);
    }

    @Transactional
    public void editProgress(Progress progress) throws Exception{
        progressDao.editProgress(progress);
        tailAfterDao.delByProgressId(progress.getId());
        tailAfterDao.insTailAfter(progress.getTailAfters());
    }

    public void editProgressTest(Progress progress){
        progressDao.editProgress(progress);
    }

    public Progress findProgressById(Integer id){
        Progress progress = progressDao.findProgressById(id);
        progress.setTailAfters(tailAfterDao.findAfterByProgId(progress.getId()));
        return progress;
    }

    @Transactional
    public void insProgress(Progress progress)throws Exception{
        progressDao.insProgress(progress);
        List<TailAfter> tailAfters = progress.getTailAfters();
        for (TailAfter tailAfter : tailAfters) {
            tailAfter.setProgressId(progress.getId());
        }
        tailAfterDao.insTailAfter(tailAfters);
    }
}
