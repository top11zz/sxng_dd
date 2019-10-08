package com.dao;

import com.entity.Progress;
import com.entity.TailAfter;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TailAfterDao {
    TailAfter selectByPrimaryKey(Integer id);

    @Delete("delete from tail_after where project_id = #{proId}")
    public void delByProId(Integer proId);

    @Delete("delete from tail_after where progress_id = #{progId}")
    public void delByProgressId(Integer progId);

    public void insTailAfter(List<TailAfter> tailAfters);

    public List<TailAfter> findAfterByProgId(Integer progId);
}