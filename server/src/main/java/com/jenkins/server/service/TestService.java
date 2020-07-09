package com.jenkins.server.service;

import com.jenkins.server.entity.MybatisTest;
import com.jenkins.server.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/8
 */
@Service
public class TestService {

    private TestMapper testMapper;

    @Autowired
    public TestService(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    public List<MybatisTest> testList()
    {
        return testMapper.getTest();
    }

}
