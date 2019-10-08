package com.dao;

import com.entity.Progress;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ProgressDao {
    Progress selectByPrimaryKey(Integer id);

    /**
     * 根据项目Id找进展列表
     * @param proId
     * @return
     */
    List<Progress> findProgressByProId(Integer proId);
    /**
     * 根据项目Id找进展列表数量
     * @param proId
     * @return
     */
     public int findProgressByProIdCount(Integer proId);
    /**
     * 根据项目id删除
     * @param proId
     */
    @Delete("delete from progress where project_id = #{proId}")
    public void delByProId(Integer proId);

    /**
     * 根据id删除
     * @param id
     */
    @Delete("delete from progress where id = #{id}")
    public void delById(Integer id);

    /**
     * 根据id获取进展
     * @param id
     * @return
     */
    public Progress findProgressById(Integer id);

    /**
     * 修改进展
     * @param progress
     * @return
     */
    public int editProgress(Progress progress);


    /**
     * 新增进展
     * @param progress
     * @return
     */
    public void insProgress(Progress progress);

}
