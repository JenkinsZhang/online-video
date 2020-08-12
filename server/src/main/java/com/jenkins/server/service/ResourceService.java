package com.jenkins.server.service;
import java.util.Collections;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.Resource;
import com.jenkins.server.entity.ResourceExample;
import com.jenkins.server.mapper.ResourceMapper;
import com.jenkins.server.model.ResourceModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.utils.CopyUtil;
import com.jenkins.server.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/10
 */
@Service
public class ResourceService {

    private ResourceMapper resourceMapper;

    @Autowired
    public ResourceService(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    public void resourceList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        ResourceExample resourceExample = new ResourceExample();
        List<Resource> resourceList = resourceMapper.selectByExample(resourceExample);
        PageInfo<Resource> pageInfo = new PageInfo<>(resourceList);
        pageModel.setTotal(pageInfo.getTotal());
        List<ResourceModel> resourceModelList = CopyUtil.copyList(resourceList, ResourceModel.class);
        pageModel.setList(resourceModelList);

    }

    public void save(ResourceModel resourceModel)
    {
        if(StringUtils.isEmpty(resourceModel.getId()))
        {
            insert(resourceModel);
        }
        else{
            update(resourceModel);
        }
    }

    public void update(ResourceModel resourceModel)
    {
        Resource copy = CopyUtil.copy(resourceModel, Resource.class);
        Date now  = new Date();
        this.resourceMapper.updateByPrimaryKey(copy);
    }

    public void insert(ResourceModel resourceModel)
    {

        Resource copy = CopyUtil.copy(resourceModel,Resource.class);
        Date now  = new Date();
        copy.setId(UuidUtil.getShortUuid());
        this.resourceMapper.insert(copy);
    }

    public void delete(String id)
    {
        resourceMapper.deleteByPrimaryKey(id);
    }

    public void saveJson(String resourceString){
        resourceMapper.deleteByExample(null);
        List<ResourceModel> resourceModels = JSON.parseArray(resourceString, ResourceModel.class);
        List<ResourceModel> result = new ArrayList<>();
        if(!CollectionUtils.isEmpty(resourceModels)){
            for (ResourceModel resourceModel : resourceModels) {
                resourceModel.setParent("");
                dfs(result,resourceModel);
            }
        }
        for (ResourceModel resourceModel : result) {
            Resource resource = new Resource();
            resource.setId(resourceModel.getId());
            resource.setName(resourceModel.getName());
            resource.setPage(resourceModel.getPage());
            resource.setParent(resourceModel.getParent());
            resource.setRequest(resourceModel.getRequest());
            resourceMapper.insert(resource);
        }
    }

    public void dfs(List<ResourceModel> result,ResourceModel model){
        result.add(model);
        List<ResourceModel> children = model.getChildren();
        if(!CollectionUtils.isEmpty(children)){
            for (ResourceModel child : children) {
                child.setParent(model.getId());
                dfs(result,child);
            }
        }
    }

    public List<ResourceModel> loadTree(){
        ResourceExample resourceExample = new ResourceExample();
        resourceExample.setOrderByClause("id asc");
        List<Resource> resources = resourceMapper.selectByExample(resourceExample);
        if(CollectionUtils.isEmpty(resources)){
           return null;
        }
        List<ResourceModel> resourceModels = CopyUtil.copyList(resources, ResourceModel.class);
        for(int i= resourceModels.size()-1;i>=0;i--){
            ResourceModel resourceModel = resourceModels.get(i);
            if("".equals(resourceModel.getParent()) || StringUtils.isEmpty(resourceModel.getParent())){
                continue;
            }else {
                for(int j = i-1;j>=0;j--){
                    if(resourceModels.get(j).getId().equals(resourceModel.getParent())){
                        ResourceModel parent = resourceModels.get(j);
                        if(CollectionUtils.isEmpty(parent.getChildren())){
                            parent.setChildren(new ArrayList<>());
                        }
                        parent.getChildren().add(0,resourceModel);
                        resourceModels.remove(i);
                    }
                }
            }
        }
        return resourceModels;


    }
}