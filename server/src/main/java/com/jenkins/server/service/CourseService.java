package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.Course;
import com.jenkins.server.entity.CourseExample;
import com.jenkins.server.mapper.CourseMapper;
import com.jenkins.server.mapper.my.MyCourseMapper;
import com.jenkins.server.model.CourseModel;
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
public class CourseService {

    private CourseMapper courseMapper;
    private MyCourseMapper myCourseMapper;

    @Autowired
    public CourseService(CourseMapper courseMapper,MyCourseMapper myCourseMapper) {
        this.courseMapper = courseMapper;
        this.myCourseMapper = myCourseMapper;
    }

    public void courseList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        CourseExample courseExample = new CourseExample();
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        pageModel.setTotal(pageInfo.getTotal());
//        List<CourseModel> courseModelList = new ArrayList<>();
//        for (Course course} : courseList) {
//            CourseModel courseModel = new CourseModel();
//            BeanUtils.copyProperties(course},courseModel);
//            courseModelList.add(courseModel);
//        }
        List<CourseModel> courseModelList = CopyUtil.copyList(courseList, CourseModel.class);
        pageModel.setList(courseModelList);

    }

    public void save(CourseModel courseModel)
    {
        if(StringUtils.isEmpty(courseModel.getId()))
        {
            insert(courseModel);
        }
        else{
            update(courseModel);
        }
    }

    public void update(CourseModel courseModel)
    {
        Course copy = CopyUtil.copy(courseModel, Course.class);
        Date now  = new Date();
        copy.setUpdatedAt(now);
        this.courseMapper.updateByPrimaryKey(copy);
    }

    public void insert(CourseModel courseModel)
    {

        Course copy = CopyUtil.copy(courseModel,Course.class);
        Date now  = new Date();
        copy.setCreatedAt(now);
        copy.setUpdatedAt(now);
        copy.setId(UuidUtil.getShortUuid());
        this.courseMapper.insert(copy);
    }

    public void delete(String id)
    {
        courseMapper.deleteByPrimaryKey(id);
    }

    public int updateTime(String courseId){
        return myCourseMapper.updateTime(courseId);
    }
}