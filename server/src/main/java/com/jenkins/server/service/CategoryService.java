package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.Category;
import com.jenkins.server.entity.CategoryExample;
import com.jenkins.server.mapper.CategoryMapper;
import com.jenkins.server.model.CategoryModel;
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
public class CategoryService {

    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public void categoryList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        CategoryExample categoryExample = new CategoryExample();
                categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        pageModel.setTotal(pageInfo.getTotal());
//        List<CategoryModel> categoryModelList = new ArrayList<>();
//        for (Category category} : categoryList) {
//            CategoryModel categoryModel = new CategoryModel();
//            BeanUtils.copyProperties(category},categoryModel);
//            categoryModelList.add(categoryModel);
//        }
        List<CategoryModel> categoryModelList = CopyUtil.copyList(categoryList, CategoryModel.class);
        pageModel.setList(categoryModelList);

    }

    public void save(CategoryModel categoryModel)
    {
        if(StringUtils.isEmpty(categoryModel.getId()))
        {
            insert(categoryModel);
        }
        else{
            update(categoryModel);
        }
    }

    public void update(CategoryModel categoryModel)
    {
        Category copy = CopyUtil.copy(categoryModel, Category.class);
        Date now  = new Date();
        this.categoryMapper.updateByPrimaryKey(copy);
    }

    public void insert(CategoryModel categoryModel)
    {

        Category copy = CopyUtil.copy(categoryModel,Category.class);
        Date now  = new Date();
        copy.setId(UuidUtil.getShortUuid());
        this.categoryMapper.insert(copy);
    }

    public void delete(String id)
    {
        categoryMapper.deleteByPrimaryKey(id);
    }
}