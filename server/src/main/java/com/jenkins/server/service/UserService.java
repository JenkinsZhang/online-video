package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.User;
import com.jenkins.server.entity.UserExample;
import com.jenkins.server.exception.BusinessCode;
import com.jenkins.server.exception.BusinessException;
import com.jenkins.server.mapper.UserMapper;
import com.jenkins.server.model.UserModel;
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
public class UserService {

    private UserMapper userMapper;

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
}