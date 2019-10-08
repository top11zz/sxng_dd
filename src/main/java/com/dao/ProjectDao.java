package com.dao;

import com.entity.Project;
import com.util.PaggingBean;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectDao {

    public Project selectByPrimaryKey(Integer id);

    public Project selectById(Integer id);

    /**
     * 所有项目
     * @return
     */

    @Select("select * from project order by created_at desc")
    public List<Project> findAllProject();

    /**
     * 通过用户id找项目id（创建人，负责人，跟踪人）
     * @param userId
     * @return
     */
    public Integer[] findProByUser(@Param("userId") String userId,@Param("tip") String tip,@Param("name") String name);

    /**
     *
     *
     * @return
     */
    public Integer[] findProByRole(@Param("tip") String tip,@Param("name") String name);

    /**
     * 通过用户id找项目id（创建人，负责人）
     * @param userId
     * @return
     */
    public Integer[] findProByUser2(String userId);

    /**
     * 通过项目Id获取项目列表
     * @param ids
     * @return
     */
    public List<Project> findProByIds(@Param("ids") Integer[] ids,@Param("first") int first,@Param("last") int last);

    /**
     *
     */
    public int findProByIdsCount(Integer[] ids);
    /**
     * 根据筛选条件获取项目Id
     * @param params
     * @return
     */
    public Integer[] findProjectIds(Map<String,Object> params);

    /**
     * 项目详情
     * @param id
     * @return
     */
    public Project findProById(Integer id);

    /**
     * 查最近3条项目标签
     * @return
     */
    public List<Project> findProTips();
    /**
     * 删除项目
     * @param id
     * @return
     */
    @Delete("delete from project where id = #{id}")
    public int deleteProject(Integer id);

    /**
     * 修改项目
     * @param project
     * @return
     */
    public int editProject(Project project);

    /**
     * 新建项目
     * @param project
     */
    public void insProject(Project project);

    /**
     * 新增后修改编号
     * @param id
     * @param number
     */
    @Update("update project set number = #{number} where id = #{id}")
    public void editProNumber(@Param("id") Integer id ,@Param("number") String number);

}