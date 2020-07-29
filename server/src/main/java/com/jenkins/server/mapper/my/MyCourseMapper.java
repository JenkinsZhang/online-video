package com.jenkins.server.mapper.my;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author JenkinsZhang
 * @date 2020/7/29
 */
@Component
@Mapper
public interface MyCourseMapper {

    int updateTime(@Param("courseId") String courseId);
}
