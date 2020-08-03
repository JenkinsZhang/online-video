package com.jenkins.server.mapper;

import com.jenkins.server.entity.CourseContent;
import com.jenkins.server.entity.CourseContentExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CourseContentMapper {
    long countByExample(CourseContentExample example);

    int deleteByExample(CourseContentExample example);

    int deleteByPrimaryKey(String id);

    int insert(CourseContent record);

    int insertSelective(CourseContent record);

    List<CourseContent> selectByExampleWithBLOBs(CourseContentExample example);

    List<CourseContent> selectByExample(CourseContentExample example);

    CourseContent selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CourseContent record, @Param("example") CourseContentExample example);

    int updateByExampleWithBLOBs(@Param("record") CourseContent record, @Param("example") CourseContentExample example);

    int updateByExample(@Param("record") CourseContent record, @Param("example") CourseContentExample example);

    int updateByPrimaryKeySelective(CourseContent record);

    int updateByPrimaryKeyWithBLOBs(CourseContent record);
}