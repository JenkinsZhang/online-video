package com.jenkins.generator.server;

import com.jenkins.server.enums.SectionChargeEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author JenkinsZhang
 * @date 2020/7/27
 */
public class ReflectionTestApplication {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<SectionChargeEnum> clazz = SectionChargeEnum.class;
        SectionChargeEnum[] enumConstants = clazz.getEnumConstants();
        Method name = clazz.getMethod("name");
        Method getCode = clazz.getMethod("getCode");
        Method getDesc = clazz.getMethod("getDesc");
        for (SectionChargeEnum enumConstant : enumConstants) {
            System.out.println(name.invoke(enumConstant));
            System.out.println(getCode.invoke(enumConstant));
            System.out.println(getDesc.invoke(enumConstant));
        }

    }
}
