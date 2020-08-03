package com.jenkins.server.mapper.my;

import com.jenkins.server.model.SortModel;
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

    int updateSort(SortModel sortModel);

    int moveSortsForward(SortModel sortModel);

    int moveSortsBackward(SortModel sortModel);
}
