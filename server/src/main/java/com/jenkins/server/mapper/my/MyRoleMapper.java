package com.jenkins.server.mapper.my;

import com.jenkins.server.model.ResourceModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/8/13
 */
@Mapper
@Component
public interface MyRoleMapper {

    List<ResourceModel> getUserResource(@Param("userId") String userId);
}
