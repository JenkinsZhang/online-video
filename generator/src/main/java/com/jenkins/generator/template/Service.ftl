package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.${Entity};
import com.jenkins.server.entity.${Entity}Example;
import com.jenkins.server.mapper.${Entity}Mapper;
import com.jenkins.server.model.${Entity}Model;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.utils.CopyUtil;
import com.jenkins.server.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/10
 */
@Service
public class ${Entity}Service {

    private ${Entity}Mapper ${entity}Mapper;

    @Autowired
    public ${Entity}Service(${Entity}Mapper ${entity}Mapper) {
        this.${entity}Mapper = ${entity}Mapper;
    }

    public void ${entity}List(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        ${Entity}Example ${entity}Example = new ${Entity}Example();
        <#list fieldList as field>
            <#if field.lowerCamelName=='sort'>
                ${entity}Example.setOrderByClause("sort asc");
            </#if>
        </#list>
        List<${Entity}> ${entity}List = ${entity}Mapper.selectByExample(${entity}Example);
        PageInfo<${Entity}> pageInfo = new PageInfo<>(${entity}List);
        pageModel.setTotal(pageInfo.getTotal());
//        List<${Entity}Model> ${entity}ModelList = new ArrayList<>();
//        for (${Entity} ${entity}} : ${entity}List) {
//            ${Entity}Model ${entity}Model = new ${Entity}Model();
//            BeanUtils.copyProperties(${entity}},${entity}Model);
//            ${entity}ModelList.add(${entity}Model);
//        }
        List<${Entity}Model> ${entity}ModelList = CopyUtil.copyList(${entity}List, ${Entity}Model.class);
        pageModel.setList(${entity}ModelList);

    }

    public void save(${Entity}Model ${entity}Model)
    {
        if(StringUtils.isEmpty(${entity}Model.getId()))
        {
            insert(${entity}Model);
        }
        else{
            update(${entity}Model);
        }
    }

    public void update(${Entity}Model ${entity}Model)
    {
        ${Entity} copy = CopyUtil.copy(${entity}Model, ${Entity}.class);
        Date now  = new Date();
        <#list fieldList as field>
            <#if field.lowerCamelName = "updatedAt">
        copy.setUpdatedAt(now);
            </#if>
        </#list>
        this.${entity}Mapper.updateByPrimaryKey(copy);
    }

    public void insert(${Entity}Model ${entity}Model)
    {

        ${Entity} copy = CopyUtil.copy(${entity}Model,${Entity}.class);
        Date now  = new Date();
        <#list fieldList as field>
            <#if field.lowerCamelName = "createdAt">
        copy.setCreatedAt(now);
            </#if>
            <#if field.lowerCamelName = "updatedAt">
        copy.setUpdatedAt(now);
            </#if>
        </#list>
        copy.setId(UuidUtil.getShortUuid());
        this.${entity}Mapper.insert(copy);
    }

    public void delete(String id)
    {
        ${entity}Mapper.deleteByPrimaryKey(id);
    }
}