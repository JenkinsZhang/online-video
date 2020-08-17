package com.jenkins.server.service;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.User;
import com.jenkins.server.entity.UserExample;
import com.jenkins.server.exception.BusinessCode;
import com.jenkins.server.exception.BusinessException;
import com.jenkins.server.mapper.UserMapper;
import com.jenkins.server.mapper.my.MyRoleMapper;
import com.jenkins.server.model.LoginModel;
import com.jenkins.server.model.ResourceModel;
import com.jenkins.server.model.UserModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.utils.CopyUtil;
import com.jenkins.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/10
 */
@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private UserMapper userMapper;

    @Autowired
    private MyRoleMapper myRoleMapper;


    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void userList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        UserExample userExample = new UserExample();
        List<User> userList = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        pageModel.setTotal(pageInfo.getTotal());
//        List<UserModel> userModelList = new ArrayList<>();
//        for (User user} : userList) {
//            UserModel userModel = new UserModel();
//            BeanUtils.copyProperties(user},userModel);
//            userModelList.add(userModel);
//        }
        List<UserModel> userModelList = CopyUtil.copyList(userList, UserModel.class);
        pageModel.setList(userModelList);

    }

    public void save(UserModel userModel)
    {
        if(StringUtils.isEmpty(userModel.getId()))
        {
            insert(userModel);
        }
        else{
            update(userModel);
        }
    }

    public void update(UserModel userModel)
    {
        User copy = CopyUtil.copy(userModel, User.class);
        copy.setPassword(null);
        this.userMapper.updateByPrimaryKeySelective(copy);
    }

    public void insert(UserModel userModel)
    {
        String loginName = userModel.getLoginName();
        User userDb = selectUserByLoginName(loginName);
        if(userDb !=null){
            throw new BusinessException(BusinessCode.USER_LOGIN_NAME_EXISTS);
        }
        else {
            User copy = CopyUtil.copy(userModel,User.class);
            copy.setId(UuidUtil.getShortUuid());
            this.userMapper.insert(copy);
        }

    }

    public void delete(String id)
    {
        userMapper.deleteByPrimaryKey(id);
    }

    public User selectUserByLoginName(String loginName){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        List<User> users = userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(users)){
            return null;
        }
        return users.get(0);
    }

    public void savePassword(UserModel userModel){
        User user = new User();
        user.setId(userModel.getId());
        user.setPassword(userModel.getPassword());
        userMapper.updateByPrimaryKeySelective(user);
    }

    public LoginModel login(UserModel userModel){
        String loginName = userModel.getLoginName();
        User user = selectUserByLoginName(loginName);
        if(user == null) {
            LOG.error(BusinessCode.USER_LOGIN_ERROR.getDesc());
            throw new BusinessException(BusinessCode.USER_LOGIN_ERROR);
        }else {
            if(userModel.getPassword().equals(user.getPassword()))
            {
                LoginModel loginModel = CopyUtil.copy(user, LoginModel.class);
                setAuth(loginModel);
                return loginModel;
            }else {
                LOG.error(BusinessCode.USER_LOGIN_ERROR.getDesc());
                throw new BusinessException(BusinessCode.USER_LOGIN_ERROR);
            }
        }
    }

    public void setAuth(LoginModel loginModel){
        List<ResourceModel> userResource = myRoleMapper.getUserResource(loginModel.getId());
        loginModel.setResources(userResource);

        HashSet<String> requestSet = new HashSet<>();
        if(!CollectionUtils.isEmpty(userResource)){
            for (ResourceModel resourceModel : userResource) {
                String request = resourceModel.getRequest();
                List<String> requests = JSON.parseArray(request, String.class);
                if(!CollectionUtils.isEmpty(requests)){
                    requestSet.addAll(requests);
                }

            }
        }
        loginModel.setRequests(requestSet);
    }
}