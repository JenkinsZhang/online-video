package com.jenkins.server.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/13
 */
public class CopyUtil {

    public static <T> List<T> copyList(List sources, Class<T> tClass) {
        List<T> target = new ArrayList<>();
        if(!CollectionUtils.isEmpty(sources))
        {
            for (Object source : sources) {
                T copy = copy(source, tClass);
                target.add(copy);
            }
        }
        return target;

    }

    public static <T> T copy(Object source, Class<T> tClass) {
        if (source == null) {
            return null;
        }
        T obj = null;
        try {
            obj = tClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(source, obj);
        return obj;


    }
}
