package com.jenkins.generator.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @author JenkinsZhang
 * @date 2020/7/15
 */
public class TemplateUtil {


    static Template template;
    public static void init(String filename) throws IOException {
        Configuration configuration  = new Configuration(Configuration.VERSION_2_3_30);
        String templateFolder = "/Users/jenkinszhang/Jobs/LinkedInProjects/onlineVideo/Services/online-video/generator/src/main/java/com/jenkins/generator/template/";
        File file = new File(templateFolder);
        configuration.setDirectoryForTemplateLoading(file);
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_30));
        template = configuration.getTemplate(filename);
    }

    public static void generate(String toPath, Map<String,Object> map) throws IOException, TemplateException {
        FileWriter writer = new FileWriter(toPath);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        template.process(map,bufferedWriter);
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
