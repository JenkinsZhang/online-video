package com.jenkins.server.service;
import java.util.Date;
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
//        List<ResourceModel> resourceModelList = new ArrayList<>();
//        for (Resource resource} : resourceList) {
//            ResourceModel resourceModel = new ResourceModel();
//            BeanUtils.copyProperties(resource},resourceModel);
//            resourceModelList.add(resourceModel);
//        }
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
}