package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.Role;
import com.jenkins.server.entity.RoleExample;
import com.jenkins.server.mapper.RoleMapper;
import com.jenkins.server.model.RoleModel;
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
public class RoleService {

    private RoleMapper roleMapper;

    @Autowired
    public RoleService(RoleMapper roleMapper) {
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

    public void save(RoleModel roleModel)
    {
        if(StringUtils.isEmpty(roleModel.getId()))
        {
            insert(roleModel);
        }
        else{
            update(roleModel);
        }
    }

    public void update(RoleModel roleModel)
    {
        Role copy = CopyUtil.copy(roleModel, Role.class);
        Date now  = new Date();
        this.roleMapper.updateByPrimaryKey(copy);
    }

    public void insert(RoleModel roleModel)
    {

        Role copy = CopyUtil.copy(roleModel,Role.class);
        Date now  = new Date();
        copy.setId(UuidUtil.getShortUuid());
        this.roleMapper.insert(copy);
    }

    public void delete(String id)
    {
        roleMapper.deleteByPrimaryKey(id);
    }
}