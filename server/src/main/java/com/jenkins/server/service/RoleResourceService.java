package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.RoleResource;
import com.jenkins.server.entity.RoleResourceExample;
import com.jenkins.server.mapper.RoleResourceMapper;
import com.jenkins.server.model.RoleModel;
import com.jenkins.server.model.RoleResourceModel;
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
public class RoleResourceService {

    private RoleResourceMapper roleResourceMapper;

    @Autowired
    public RoleResourceService(RoleResourceMapper roleResourceMapper) {
        this.roleResourceMapper = roleResourceMapper;
    }

    public void roleResourceList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        List<RoleResource> roleResourceList = roleResourceMapper.selectByExample(roleResourceExample);
        PageInfo<RoleResource> pageInfo = new PageInfo<>(roleResourceList);
        pageModel.setTotal(pageInfo.getTotal());
//        List<RoleResourceModel> roleResourceModelList = new ArrayList<>();
//        for (RoleResource roleResource} : roleResourceList) {
//            RoleResourceModel roleResourceModel = new RoleResourceModel();
//            BeanUtils.copyProperties(roleResource},roleResourceModel);
//            roleResourceModelList.add(roleResourceModel);
//        }
        List<RoleResourceModel> roleResourceModelList = CopyUtil.copyList(roleResourceList, RoleResourceModel.class);
        pageModel.setList(roleResourceModelList);

    }

    public void save(RoleResourceModel roleResourceModel)
    {
        if(StringUtils.isEmpty(roleResourceModel.getId()))
        {
            insert(roleResourceModel);
        }
        else{
            update(roleResourceModel);
        }
    }

    public void update(RoleResourceModel roleResourceModel)
    {
        RoleResource copy = CopyUtil.copy(roleResourceModel, RoleResource.class);
        Date now  = new Date();
        this.roleResourceMapper.updateByPrimaryKey(copy);
    }

    public void insert(RoleResourceModel roleResourceModel)
    {

        RoleResource copy = CopyUtil.copy(roleResourceModel,RoleResource.class);
        Date now  = new Date();
        copy.setId(UuidUtil.getShortUuid());
        this.roleResourceMapper.insert(copy);
    }

    public void delete(String id)
    {
        roleResourceMapper.deleteByPrimaryKey(id);
    }

    public void saveBatch(RoleModel roleModel){
        List<String> resources = roleModel.getResources();
        String roleId = roleModel.getId();
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        roleResourceExample.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceMapper.deleteByExample(roleResourceExample);
        for (String resource : resources) {
            RoleResourceModel roleResourceModel = new RoleResourceModel();
            roleResourceModel.setRoleId(roleId);
            roleResourceModel.setResourceId(resource);
            insert(roleResourceModel);
        }
    }
}