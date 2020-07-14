package com.jenkins.server.utils;

import com.jenkins.server.exception.ValidatorException;
import org.springframework.util.StringUtils;

/**
 * @author JenkinsZhang
 * @date 2020/7/14
 */
public class ValidatorUtil {

    public static void require(Object str,String fieldName)
    {
        if(StringUtils.isEmpty(str))
        {
            throw new ValidatorException(fieldName+" can't be empty!");
        }
    }

    public static void length(String str,int min,int max,String fieldName)
    {
        if (StringUtils.isEmpty(str)) {
            return;
        }
        int length = 0;
        if (!StringUtils.isEmpty(str)) {
            length = str.length();
        }
        if (length < min || length > max) {
            throw new ValidatorException(fieldName + "'s length are " + min + "~" + max + "characters");
        }
    }
}
