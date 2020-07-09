package com.jenkins.server.mapper;

import com.jenkins.server.entity.MybatisTest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/8
 */

@Mapper
@Component
public interface TestMapper {

    public List<MybatisTest> getTest();
}
