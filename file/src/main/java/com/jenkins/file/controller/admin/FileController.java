package com.jenkins.file.controller.admin;

import com.jenkins.server.model.FileModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.FileService;
import com.jenkins.server.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JenkinsZhang
 * @date 2020/7/7
 */

@RestController
@RequestMapping("/admin/file")
public class FileController {

    private FileService fileService;
    public static final String BUSINESS_NAME = "File";

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/list")
    public ResponseModel getFileList (@RequestBody PageModel pageModel){

        fileService.fileList(pageModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(pageModel);
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel save(@RequestBody FileModel fileModel)
    {
        ValidatorUtil.require(fileModel.getPath(),"Path");
        ValidatorUtil.length(fileModel.getPath(), 1, 100, "Path");
        ValidatorUtil.length(fileModel.getName(), 1, 100, "Name");
        ValidatorUtil.length(fileModel.getSuffix(), 1, 10, "Suffix");
        ValidatorUtil.length(fileModel.getKey(), 1, 32, "Key");
        fileService.save(fileModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(fileModel);
        return responseModel;
    }

}
