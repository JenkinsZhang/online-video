package com.jenkins.business.controller.admin;

import com.jenkins.server.model.CategoryModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.CategoryService;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    private CategoryService categoryService;
    public static final String BUSINESS_NAME = "Category";

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/list")
    public ResponseModel getCategoryList (@RequestBody PageModel pageModel){

        categoryService.categoryList(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody CategoryModel categoryModel)
    {
        ValidatorUtil.require(categoryModel.getParent(),"Parent");        ValidatorUtil.require(categoryModel.getName(),"Name");
        ValidatorUtil.length(categoryModel.getName(), 1, 50, "Name");
        categoryService.save(categoryModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(categoryModel);
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") String id)
    {
        categoryService.delete(id);
        ResponseModel responseModel= new ResponseModel();
        return responseModel;
    }
}
