package com.jenkins.server.service;
import java.util.*;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.*;
import com.jenkins.server.enums.CourseStatusEnum;
import com.jenkins.server.mapper.*;
import com.jenkins.server.mapper.my.MyCourseMapper;
import com.jenkins.server.model.*;
import com.jenkins.server.utils.CopyUtil;
import com.jenkins.server.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
    private CategoryMapper categoryMapper;
    @Autowired
    private CourseCategoryMapper courseCategoryMapper;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private CourseContentMapper courseContentMapper;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private TeacherService teacherService;

    public void courseList(CoursePageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria criteria = courseExample.createCriteria();
        if(!StringUtils.isEmpty(pageModel.getStatus())){
            criteria.andStatusEqualTo(pageModel.getStatus());
        }
        courseExample.setOrderByClause("sort asc");
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        pageModel.setTotal(pageInfo.getTotal());
        List<CourseModel> courseModelList = CopyUtil.copyList(courseList, CourseModel.class);
        pageModel.setList(courseModelList);

    }

    public void webCourseList(CoursePageModel pageModel) {
        String categoryId = pageModel.getCategoryId();
        if (StringUtils.isEmpty(categoryId)) {
            courseList(pageModel);
            return;
        }
        HashSet<String> courseIds = new LinkedHashSet<>();
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria1 = categoryExample.createCriteria();
        criteria1.andIdEqualTo(categoryId);
        CategoryExample.Criteria criteria2 = categoryExample.createCriteria();
        criteria2.andParentEqualTo(categoryId);
        categoryExample.or(criteria2);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        if(categories.size() == 0){
            pageModel.setTotal(0);
            pageModel.setList(new ArrayList<CourseModel>());
            return;
        }
        for (Category category : categories) {
            String categoryIdDB = category.getId();
            CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
            courseCategoryExample.createCriteria().andCategoryIdEqualTo(categoryIdDB);
            List<CourseCategory> courseCategories = courseCategoryMapper.selectByExample(courseCategoryExample);
            if(!CollectionUtils.isEmpty(courseCategories)){
                for (CourseCategory courseCategory : courseCategories) {
                    String courseId = courseCategory.getCourseId();
                    courseIds.add(courseId);
                }
            }
        }
        ArrayList<String> listCourseIds = new ArrayList<>();
        listCourseIds.addAll(courseIds);
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        CourseExample courseExample = new CourseExample();
        if(courseIds.size() !=0){
            courseExample.createCriteria().andIdIn(listCourseIds);
        }
        else {
            pageModel.setTotal(0);
            pageModel.setList(new ArrayList<CourseModel>());
            return;
        }
        courseExample.setOrderByClause("sort asc");
        List<Course> courses = courseMapper.selectByExample(courseExample);
        PageInfo<Course> pageInfo = new PageInfo<>(courses);
        pageModel.setTotal(pageInfo.getTotal());
        pageModel.setList(CopyUtil.copyList(courses,CourseModel.class));

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
    public void deleteChildrenChapters(String courseId)
    {
        List<Chapter> chapters = chapterService.chapterList(courseId);
        for (Chapter chapter : chapters) {
            chapterService.delete(chapter.getId());
        }
    }

    public int updateTime(String courseId){
        return myCourseMapper.updateTime(courseId);
    }

    public CourseContentModel findCourseContent(String id)
    {
        CourseContent courseContent = courseContentMapper.selectByPrimaryKey(id);
        if(courseContent == null)
        {
            return  null;
        }
        return CopyUtil.copy(courseContent,CourseContentModel.class);
    }

    public void saveContent(CourseContentModel courseContentModel)
    {
        CourseContent courseContent = CopyUtil.copy(courseContentModel,CourseContent.class);
        int i = courseContentMapper.updateByPrimaryKeyWithBLOBs(courseContent);
        if(i ==0)
        {
            courseContentMapper.insert(courseContent);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void sort(SortModel sortModel) {

        myCourseMapper.updateSort(sortModel);


        if (sortModel.getNewSort() > sortModel.getOldSort()) {
            myCourseMapper.moveSortsForward(sortModel);
        }


        if (sortModel.getNewSort() < sortModel.getOldSort()) {
            myCourseMapper.moveSortsBackward(sortModel);
        }
    }

    public List<CourseModel> listNewCourses(){
        PageHelper.startPage(1,3);
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria().andStatusEqualTo(CourseStatusEnum.PUBLISH.getCode());
        courseExample.setOrderByClause("created_at desc");
        List<Course> courses = courseMapper.selectByExample(courseExample);

        return CopyUtil.copyList(courses,CourseModel.class);
    }

    public CourseModel findCourse(String id){
        Course course = courseMapper.selectByPrimaryKey(id);
        if(course == null || !course.getStatus().equals(CourseStatusEnum.PUBLISH.getCode())){
            return null;
        }
        CourseModel courseModel = CopyUtil.copy(course, CourseModel.class);
        CourseContent courseContent = courseContentMapper.selectByPrimaryKey(id);
        if(courseContent!=null){
            courseModel.setContent(courseContent.getContent());
        }
        List<ChapterModel> chapterModels = chapterService.listByCourse(id);
        if(!CollectionUtils.isEmpty(chapterModels)){
            courseModel.setChapterModels(chapterModels);
        }
        List<SectionModel> sectionModels = sectionService.listByCourse(id);
        if(!CollectionUtils.isEmpty(sectionModels)){
            courseModel.setSectionModels(sectionModels);
        }
        TeacherModel teacherModel = teacherService.findById(course.getTeacherId());
        if(teacherModel!=null){
            courseModel.setTeacherModel(teacherModel);
        }

        return courseModel;
    }

}