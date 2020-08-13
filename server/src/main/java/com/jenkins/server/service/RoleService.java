package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.Role;
import com.jenkins.server.entity.RoleExample;
import com.jenkins.server.entity.RoleResource;
import com.jenkins.server.entity.RoleResourceExample;
import com.jenkins.server.mapper.RoleMapper;
import com.jenkins.server.mapper.RoleResourceMapper;
import com.jenkins.server.model.RoleModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.RoleResourceModel;
import com.jenkins.server.utils.CopyUtil;
import com.jenkins.server.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/10
 */
@Service
@EnableTransactionManagement
public class RoleService {

    private RoleMapper roleMapper;

    private RoleResourceMapper roleResourceMapper;

    @Autowired
    private  RoleResourceService resourceService;

    @Autowired
    public RoleService(RoleMapper roleMapper,RoleResourceMapper roleResourceMapper) {
        this.roleResourceMapper = roleResourceMapper;
        this.roleMapper = roleMapper;
    }

    public void roleList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        RoleExample roleExample = new RoleExample();
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        pageModel.setTotal(pageInfo.getTotal());
//        List<RoleModel> roleModelList = new ArrayList<>();
//        for (Role role} : roleList) {
//            RoleModel roleModel = new RoleModel();
//            BeanUtils.copyProperties(role},roleModel);
//            roleModelList.add(roleModel);
//        }
        List<RoleModel> roleModelList = CopyUtil.copyList(roleList, RoleModel.class);
        pageModel.setList(roleModelList);

    }

    @Transactional(rollbackFor = Exception.class)
    public void save(RoleModel roleModel)
    {
        if(StringUtils.isEmpty(roleModel.getId()))
        {
            String insert = insert(roleModel);
            roleModel.setId(insert);
            resourceService.saveBatch(roleModel);
        }
        else{
            update(roleModel);
            resourceService.saveBatch(roleModel);
        }
    }

    public void update(RoleModel roleModel)
    {
        Role copy = CopyUtil.copy(roleModel, Role.class);
        Date now  = new Date();
        this.roleMapper.updateByPrimaryKey(copy);
    }

    public String insert(RoleModel roleModel)
    {

        Role copy = CopyUtil.copy(roleModel,Role.class);
        Date now  = new Date();
        String shortUuid = UuidUtil.getShortUuid();
        copy.setId(shortUuid);
        this.roleMapper.insert(copy);
        return shortUuid;

    }

    public void delete(String id)
    {
        roleMapper.deleteByPrimaryKey(id);
    }

    public List<RoleResourceModel> listResource(String roleId){
        RoleResourceExample roleResourceExample = new RoleResourceExample();
        roleResourceExample.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleResource> roleResources = roleResourceMapper.selectByExample(roleResourceExample);
        return CopyUtil.copyList(roleResources,RoleResourceModel.class);
    }

}