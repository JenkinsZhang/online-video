package com.jenkins.server.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import net.sf.jsqlparser.statement.select.Join;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * @author JenkinsZhang
 * @date 2020/7/14
 */

@Aspect
@Component
public class LogAspect {


    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.jenkins.*.controller..*Controller.*(..))")
    public void controllerPointcut()
    { }

    @Before(value = "controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable
    {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();

        String nameCn = "";
        if (name.contains("list") || name.contains("query")) {
            nameCn = "Query";
        } else if (name.contains("save")) {
            nameCn = "Save";
        } else if (name.contains("delete")) {
            nameCn = "Delete";
        } else {
            nameCn = "Operate";
        }

        Class clazz = signature.getDeclaringType();
        String businessName = "";
        Field field;
        try {
            field = clazz.getField("BUSINESS_NAME");
            if (!StringUtils.isEmpty(field)) {
                businessName = (String) field.get(clazz);
            }
        } catch (NoSuchFieldException e) {
        LOG.error("Can't acquire business name");
         } catch (SecurityException e) {
        LOG.error("Acquire business name failed", e);
        }

        LOG.info("------------------------------【{}】{} ------------------------------has begun",businessName,nameCn);
        LOG.info("Requesting URL: {}",request.getRequestURL().toString());
        LOG.info("Remoting Address: {}",request.getRemoteAddr());
        LOG.info("Class name & method: {} {}",signature.getDeclaringTypeName(),name);


        Object[] args = joinPoint.getArgs();
        Object[] arguments = new Object[args.length];

        for(int i=0;i<args.length;i++)
        {
            if(args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile)
            {
                continue;
            }
            arguments[i] = args[i];
        }
        String[] exclude = {"shard"};

        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter mySimplePropertyPreFilter = filters.addFilter(exclude);
        mySimplePropertyPreFilter.addExcludes(exclude);

        LOG.info("Requesting Parameters: {}",JSONObject.toJSONString(arguments));
    }


    @Around("controllerPointcut()")
    public Object doAfter(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        String[] excludes = {"shard","password"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter mySimplePropertyPreFilter = filters.addFilter(excludes);
        mySimplePropertyPreFilter.addExcludes(excludes);

        LOG.info("Returning result {}",JSONObject.toJSONString(proceed));
        LOG.info("------------------------------Finished. Using Time:【{} ms】------------------------------",System.currentTimeMillis()-startTime);
        return proceed;
    }
}
