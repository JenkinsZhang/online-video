package com.jenkins.system.controller;

import com.jenkins.server.entity.MybatisTest;
import com.jenkins.server.mapper.TestMapper;
import com.jenkins.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
public class TestController {

    private TestService testService;

    private TestMapper testMapper;


    @Autowired
    public TestController(TestService testService,TestMapper testMapper) {
        this.testService = testService;
        this.testMapper = testMapper;
    }

    @RequestMapping("/test")
    public List<MybatisTest> helloWorld(){

        return testService.testList();
    }

    @GetMapping("/test2")
    public List<MybatisTest> test(){
        return testMapper.getTest();
    }


    @GetMapping("/test3")
    public String test2(){
        return "hello world!";
    }

}
