package com.jenkins.generator.server;

import com.jenkins.generator.utils.DbUtil;
import com.jenkins.generator.utils.Field;
import com.jenkins.generator.utils.TemplateUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * @author JenkinsZhang
 * @date 2020/7/15
 */
public class GeneratorApplication {

    static String Module= "business";

    static String toServicePath="/Users/jenkinszhang/Jobs/LinkedInProjects/onlineVideo/Services/online-video/server/src/main/java/com/jenkins/server/service/" ;
    static String toModelPath="/Users/jenkinszhang/Jobs/LinkedInProjects/onlineVideo/Services/online-video/server/src/main/java/com/jenkins/server/model/" ;
    static String toControllerPath="/Users/jenkinszhang/Jobs/LinkedInProjects/onlineVideo/Services/online-video/business/src/main/java/com/jenkins/"+Module+"/controller/admin/" ;
    static String toVuePath = "/Users/jenkinszhang/Jobs/LinkedInProjects/onlineVideo/admin/src/views/admin/";
    static String sqlGeneratorXmlPath = "/Users/jenkinszhang/Jobs/LinkedInProjects/onlineVideo/Services/online-video/server/src/main/resources/generator/generatorConfig.xml";

    public static void main(String[] args) throws IOException, TemplateException, DocumentException, SQLException {
        File file = new File(sqlGeneratorXmlPath);
        SAXReader reader = new SAXReader();
        Document read = reader.read(file);
        Element rootElement = read.getRootElement();
        Element context = rootElement.element("context");
        Element firstTable = context.element("table");
        String tableName = firstTable.attributeValue("tableName");
        String entity = firstTable.attributeValue("domainObjectName");

        String module = Module;

        List<Field> fieldList = DbUtil.getColumnsByTableName(tableName);
        Set<String> typeSet = new HashSet<>();
        for (Field field : fieldList) {
            typeSet.add(field.getJavaType());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("Entity",entity);
        map.put("entity",entity.toLowerCase());
        map.put("module",module);
        map.put("fieldList",fieldList);
        map.put("typeSet",typeSet);
        //Services
        TemplateUtil.init("Service.ftl");
        TemplateUtil.generate(toServicePath+entity+"Service.java",map);

        //Controller
        TemplateUtil.init("Controller.ftl");
        TemplateUtil.generate(toControllerPath+entity+"Controller.java",map);

        //Model
//        TemplateUtil.init("Model.ftl");
//        TemplateUtil.generate(toModelPath+entity+"Model.java",map);

        //Vue
        TemplateUtil.init("Vue.ftl");
        TemplateUtil.generate(toVuePath+entity+".vue",map);
    }
}
