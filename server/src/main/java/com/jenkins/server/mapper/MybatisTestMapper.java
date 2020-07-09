package com.jenkins.server.mapper;

import com.jenkins.server.entity.MybatisTest;
import com.jenkins.server.entity.MybatisTestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MybatisTestMapper {
    long countByExample(MybatisTestExample example);

    int deleteByExample(MybatisTestExample example);

    int deleteByPrimaryKey(String id);

    int insert(MybatisTest record);

    int insertSelective(MybatisTest record);

    List<MybatisTest> selectByExample(MybatisTestExample example);

    MybatisTest selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MybatisTest record, @Param("example") MybatisTestExample example);

    int updateByExample(@Param("record") MybatisTest record, @Param("example") MybatisTestExample example);

    int updateByPrimaryKeySelective(MybatisTest record);

    int updateByPrimaryKey(MybatisTest record);
}