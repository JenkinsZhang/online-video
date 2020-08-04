package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.CategoryExample;
import com.jenkins.server.entity.Teacher;
import com.jenkins.server.entity.TeacherExample;
import com.jenkins.server.mapper.TeacherMapper;
import com.jenkins.server.model.TeacherModel;
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
public class TeacherService {

    private TeacherMapper teacherMapper;

    @Autowired
    public TeacherService(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    public void teacherList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        TeacherExample teacherExample = new TeacherExample();
        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
        pageModel.setTotal(pageInfo.getTotal());
//        List<TeacherModel> teacherModelList = new ArrayList<>();
//        for (Teacher teacher} : teacherList) {
//            TeacherModel teacherModel = new TeacherModel();
//            BeanUtils.copyProperties(teacher},teacherModel);
//            teacherModelList.add(teacherModel);
//        }
        List<TeacherModel> teacherModelList = CopyUtil.copyList(teacherList, TeacherModel.class);
        pageModel.setList(teacherModelList);

    }

    public void save(TeacherModel teacherModel)
    {
        if(StringUtils.isEmpty(teacherModel.getId()))
        {
            insert(teacherModel);
        }
        else{
            update(teacherModel);
        }
    }

    public void update(TeacherModel teacherModel)
    {
        Teacher copy = CopyUtil.copy(teacherModel, Teacher.class);
        Date now  = new Date();
        this.teacherMapper.updateByPrimaryKey(copy);
    }

    public void insert(TeacherModel teacherModel)
    {

        Teacher copy = CopyUtil.copy(teacherModel,Teacher.class);
        Date now  = new Date();
        copy.setId(UuidUtil.getShortUuid());
        this.teacherMapper.insert(copy);
    }

    public void delete(String id)
    {
        teacherMapper.deleteByPrimaryKey(id);
    }

    public List<TeacherModel> all()
    {
        TeacherExample teacherExample = new TeacherExample();
        teacherExample.setOrderByClause("name asc");
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        return CopyUtil.copyList(teachers,TeacherModel.class);
    }
}