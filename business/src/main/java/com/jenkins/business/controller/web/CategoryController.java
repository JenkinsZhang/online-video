package com.jenkins.business.controller.web;

import com.jenkins.server.model.CategoryModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/8/17
 */
@RestController("webCategoryController")
@RequestMapping("/web/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/all")
    public ResponseModel getCategoryList (){

        List<CategoryModel> all = categoryService.all();
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(all);
        return responseModel;
    }
}
