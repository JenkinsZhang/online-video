package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.CourseCategory;
import com.jenkins.server.entity.CourseCategoryExample;
import com.jenkins.server.mapper.CourseCategoryMapper;
import com.jenkins.server.model.CategoryModel;
import com.jenkins.server.model.CourseCategoryModel;
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
@EnableTransactionManagement
public class CourseCategoryService {

    private CourseCategoryMapper coursecategoryMapper;

    @Autowired
    public CourseCategoryService(CourseCategoryMapper coursecategoryMapper) {
        this.coursecategoryMapper = coursecategoryMapper;
    }

    public void coursecategoryList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        CourseCategoryExample coursecategoryExample = new CourseCategoryExample();
        List<CourseCategory> coursecategoryList = coursecategoryMapper.selectByExample(coursecategoryExample);
        PageInfo<CourseCategory> pageInfo = new PageInfo<>(coursecategoryList);
        pageModel.setTotal(pageInfo.getTotal());
//        List<CourseCategoryModel> coursecategoryModelList = new ArrayList<>();
//        for (CourseCategory coursecategory} : coursecategoryList) {
//            CourseCategoryModel coursecategoryModel = new CourseCategoryModel();
//            BeanUtils.copyProperties(coursecategory},coursecategoryModel);
//            coursecategoryModelList.add(coursecategoryModel);
//        }
        List<CourseCategoryModel> coursecategoryModelList = CopyUtil.copyList(coursecategoryList, CourseCategoryModel.class);
        pageModel.setList(coursecategoryModelList);

    }

    public void save(CourseCategoryModel coursecategoryModel)
    {
        if(StringUtils.isEmpty(coursecategoryModel.getId()))
        {
            insert(coursecategoryModel);
        }
        else{
            update(coursecategoryModel);
        }
    }

    public void update(CourseCategoryModel coursecategoryModel)
    {
        CourseCategory copy = CopyUtil.copy(coursecategoryModel, CourseCategory.class);
        Date now  = new Date();
        this.coursecategoryMapper.updateByPrimaryKey(copy);
    }

    public void insert(CourseCategoryModel coursecategoryModel)
    {

        CourseCategory copy = CopyUtil.copy(coursecategoryModel,CourseCategory.class);
        Date now  = new Date();
        copy.setId(UuidUtil.getShortUuid());
        this.coursecategoryMapper.insert(copy);
    }

    public void delete(String id)
    {
        coursecategoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(String courseId,List<CategoryModel> categoryModels)
    {
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        courseCategoryExample.createCriteria().andCourseIdEqualTo(courseId);
        coursecategoryMapper.deleteByExample(courseCategoryExample);
        for (CategoryModel categoryModel : categoryModels) {
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setId(UuidUtil.getShortUuid());
            courseCategory.setCategoryId(categoryModel.getId());
            courseCategory.setCourseId(courseId);
           coursecategoryMapper.insert(courseCategory);
        }

    }


    public List<CourseCategoryModel> listByCourse(String courseId){
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        courseCategoryExample.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseCategory> courseCategories = coursecategoryMapper.selectByExample(courseCategoryExample);
        List<CourseCategoryModel> courseCategoryModels = CopyUtil.copyList(courseCategories, CourseCategoryModel.class);
        return  courseCategoryModels;
    }
}