package com.jenkins.server.service;

import java.util.Date;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.RoleUser;
import com.jenkins.server.entity.RoleUserExample;
import com.jenkins.server.mapper.RoleUserMapper;
import com.jenkins.server.model.RoleModel;
import com.jenkins.server.model.RoleUserModel;
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
public class RoleUserService {

    private RoleUserMapper roleUserMapper;

    @Autowired
    public RoleUserService(RoleUserMapper roleUserMapper) {
        this.roleUserMapper = roleUserMapper;
    }

    public void roleUserList(PageModel pageModel) {
        PageHelper.startPage(pageModel.getPage(), pageModel.getPageSize());
        RoleUserExample roleUserExample = new RoleUserExample();
        List<RoleUser> roleUserList = roleUserMapper.selectByExample(roleUserExample);
        PageInfo<RoleUser> pageInfo = new PageInfo<>(roleUserList);
        pageModel.setTotal(pageInfo.getTotal());
//        List<RoleUserModel> roleUserModelList = new ArrayList<>();
//        for (RoleUser roleUser} : roleUserList) {
//            RoleUserModel roleUserModel = new RoleUserModel();
//            BeanUtils.copyProperties(roleUser},roleUserModel);
//            roleUserModelList.add(roleUserModel);
//        }
        List<RoleUserModel> roleUserModelList = CopyUtil.copyList(roleUserList, RoleUserModel.class);
        pageModel.setList(roleUserModelList);

    }

    public void save(RoleUserModel roleUserModel) {
        if (StringUtils.isEmpty(roleUserModel.getId())) {
            insert(roleUserModel);
        } else {
            update(roleUserModel);
        }
    }

    public void update(RoleUserModel roleUserModel) {
        RoleUser copy = CopyUtil.copy(roleUserModel, RoleUser.class);
        Date now = new Date();
        this.roleUserMapper.updateByPrimaryKey(copy);
    }

    public void insert(RoleUserModel roleUserModel) {

        RoleUser copy = CopyUtil.copy(roleUserModel, RoleUser.class);
        Date now = new Date();
        copy.setId(UuidUtil.getShortUuid());
        this.roleUserMapper.insert(copy);
    }

    public void delete(String id) {
        roleUserMapper.deleteByPrimaryKey(id);
    }

    public void saveBatch(RoleModel roleModel) {
        List<String> users = roleModel.getUsers();
        if (CollectionUtils.isEmpty(users)) {
            return;
        }
        String roleId = roleModel.getId();
        RoleUserExample roleUserExample = new RoleUserExample();
        roleUserExample.createCriteria().andRoleIdEqualTo(roleId);
        roleUserMapper.deleteByExample(roleUserExample);

        for (String user : users) {
            RoleUserModel roleUserModel = new RoleUserModel();
            roleUserModel.setRoleId(roleId);
            roleUserModel.setUserId(user);
            insert(roleUserModel);
        }
    }
}