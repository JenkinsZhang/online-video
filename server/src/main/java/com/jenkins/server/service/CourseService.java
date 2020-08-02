package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.Chapter;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/10
 */
@Service
@EnableTransactionManagement()
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private MyCourseMapper myCourseMapper;
    @Autowired
    private CourseCategoryService courseCategoryService;
    @Autowired
    private ChapterService chapterService;

//    @Autowired
//    public CourseService(CourseMapper courseMapper, MyCourseMapper myCourseMapper, CourseCategoryService courseCategoryService, ChapterService chapterService) {
//        this.courseMapper = courseMapper;
//        this.myCourseMapper = myCourseMapper;
//        this.courseCategoryService = courseCategoryService;
//        this.chapterService = chapterService;
//    }

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
    @Transactional(rollbackFor = Exception.class)
    public void save(CourseModel courseModel)
    {
        if(StringUtils.isEmpty(courseModel.getId()))
        {
            String courseId = insert(courseModel);
            courseCategoryService.saveBatch(courseId,courseModel.getCategorys());
        }
        else{
            update(courseModel);
            courseCategoryService.saveBatch(courseModel.getId(),courseModel.getCategorys());
        }

    }

    public void update(CourseModel courseModel)
    {
        Course copy = CopyUtil.copy(courseModel, Course.class);
        Date now  = new Date();
        copy.setUpdatedAt(now);
        this.courseMapper.updateByPrimaryKey(copy);
    }

    public String insert(CourseModel courseModel)
    {

        Course copy = CopyUtil.copy(courseModel,Course.class);
        Date now  = new Date();
        copy.setCreatedAt(now);
        copy.setUpdatedAt(now);
        copy.setId(UuidUtil.getShortUuid());
        this.courseMapper.insert(copy);
        return copy.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String id)
    {
        deleteChildrenChapters(id);
        courseMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteChildrenChapters(String id)
    {
        List<Chapter> chapters = chapterService.chapterList(id);
        for (Chapter chapter : chapters) {
            chapterService.delete(chapter.getId());
        }
    }

    public int updateTime(String courseId){
        return myCourseMapper.updateTime(courseId);
    }
}