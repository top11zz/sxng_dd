package com.dao;

import com.entity.Principal;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PrincipalDao {
    Principal selectByPrimaryKey(Integer id);

    @Delete("delete from principal where project_id = #{proId}")
    public void delPrinByPro(Integer proId);

    public void insPrinByPro(List<Principal> principals);

    public List<Principal> findPrinByProId(Integer proId);

}