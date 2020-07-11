package com.jenkins.server.service;

import com.jenkins.server.entity.MybatisTest;
import com.jenkins.server.entity.MybatisTestExample;
import com.jenkins.server.mapper.MybatisTestMapper;
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

    private MybatisTestMapper mybatisTestMapper;

    @Autowired
    public TestService(MybatisTestMapper mybatisTestMapper) {
        this.mybatisTestMapper = mybatisTestMapper;
    }

    public List<MybatisTest> testList()
    {
        MybatisTestExample mybatisTestExample = new MybatisTestExample();
        mybatisTestExample.createCriteria().andIdEqualTo("1");
        return mybatisTestMapper.selectByExample(mybatisTestExample);
    }

}
