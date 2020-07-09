package com.jenkins.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * @author JenkinsZhang
 * @date 2020/7/9
 */

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.jenkins")
@MapperScan(basePackages = "com.jenkins.server.mapper")
public class SystemApplication {

    private static final Logger LOG = LoggerFactory.getLogger(SystemApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SystemApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("Starting Successfully!");
        LOG.info("System address: http://localhost:{}",env.getProperty("server.port"));
    }
}
