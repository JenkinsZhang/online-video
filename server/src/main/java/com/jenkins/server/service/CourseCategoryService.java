package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.CourseCategory;
import com.jenkins.server.entity.CourseCategoryExample;
import com.jenkins.server.mapper.CourseCategoryMapper;
import com.jenkins.server.model.CategoryModel;
import com.jenkins.server.model.CourseCategoryModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.utils.CopyUtil;
import com.jenkins.server.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/10
 */
@Service
@EnableTransactionManagement
public class CourseCategoryService {

    private CourseCategoryMapper courseCategoryMapper;

    @Autowired
    public CourseCategoryService(CourseCategoryMapper courseCategoryMapper) {
        this.courseCategoryMapper = courseCategoryMapper;
    }

    public void courseCategoryList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(courseCategoryExample);
        PageInfo<CourseCategory> pageInfo = new PageInfo<>(courseCategoryList);
        pageModel.setTotal(pageInfo.getTotal());
        List<CourseCategoryModel> courseCategoryModelList = CopyUtil.copyList(courseCategoryList, CourseCategoryModel.class);
        pageModel.setList(courseCategoryModelList);

    }

    public void save(CourseCategoryModel courseCategoryModel)
    {
        if(StringUtils.isEmpty(courseCategoryModel.getId()))
        {
            insert(courseCategoryModel);
        }
        else{
            update(courseCategoryModel);
        }
    }

    public void update(CourseCategoryModel courseCategoryModel)
    {
        CourseCategory copy = CopyUtil.copy(courseCategoryModel, CourseCategory.class);
        Date now  = new Date();
        this.courseCategoryMapper.updateByPrimaryKey(copy);
    }

    public void insert(CourseCategoryModel courseCategoryModel)
    {

        CourseCategory copy = CopyUtil.copy(courseCategoryModel,CourseCategory.class);
        Date now  = new Date();
        copy.setId(UuidUtil.getShortUuid());
        this.courseCategoryMapper.insert(copy);
    }

    public void delete(String id)
    {
        courseCategoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(String courseId,List<CategoryModel> categoryModels)
    {
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        courseCategoryExample.createCriteria().andCourseIdEqualTo(courseId);
        courseCategoryMapper.deleteByExample(courseCategoryExample);
        for (CategoryModel categoryModel : categoryModels) {
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setId(UuidUtil.getShortUuid());
            courseCategory.setCategoryId(categoryModel.getId());
            courseCategory.setCourseId(courseId);
           courseCategoryMapper.insert(courseCategory);
        }

    }


    public List<CourseCategoryModel> listByCourse(String courseId){
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        courseCategoryExample.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseCategory> courseCategories = courseCategoryMapper.selectByExample(courseCategoryExample);
        List<CourseCategoryModel> courseCategoryModels = CopyUtil.copyList(courseCategories, CourseCategoryModel.class);
        return  courseCategoryModels;
    }
}