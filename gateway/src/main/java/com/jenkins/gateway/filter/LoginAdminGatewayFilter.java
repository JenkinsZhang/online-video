package com.jenkins.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author JenkinsZhang
 * @date 2020/8/12
 */
@Component
public class LoginAdminGatewayFilter implements GatewayFilter,Ordered  {

    private static final Logger LOG = LoggerFactory.getLogger(LoginAdminGatewayFilter.class);

    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if(!path.contains("admin")){
            return chain.filter(exchange);
        }
        if(path.contains("login")||path.contains("logout")||path.contains("kaptcha")){
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getHeaders().getFirst("Token");
        if(token == null || token.isEmpty()){
            LOG.info("Empty token!");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        Object loginUser = redisTemplate.opsForValue().get(token);
        if(loginUser == null){
            LOG.warn("Wrong token!");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }else {
            LOG.info("backend interceptor:" + token);
            return chain.filter(exchange);
        }

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
