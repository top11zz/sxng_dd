package com.dao;

import com.entity.FirstLinkman;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@Mapper
public interface FirstLinkmanDao {

    FirstLinkman selectByPrimaryKey(Integer id);

    @Delete("delete from first_linkman where project_id = #{proId}")
    public void delLinkmanByPro(Integer proId);

    public void insLinkmanByPro(List<FirstLinkman> firstLinkmen);

    public List<FirstLinkman> findFirstLinkmenByProId(Integer proId);
}