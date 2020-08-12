package com.jenkins.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @author JenkinsZhang
 * @date 2020/8/12
 */
@Component
public class LoginAdminGatewayFilterFactory extends AbstractGatewayFilterFactory {

    @Autowired
    LoginAdminGatewayFilter loginAdminGatewayFilter;

    @Override
    public GatewayFilter apply(Object config) {
        return loginAdminGatewayFilter;
    }
}
