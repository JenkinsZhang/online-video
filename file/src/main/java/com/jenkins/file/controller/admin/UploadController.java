package com.jenkins.file.controller.admin;

import com.jenkins.server.enums.FileUseEnum;
import com.jenkins.server.model.FileModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.FileService;
import com.jenkins.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author JenkinsZhang
 * @date 2020/8/4
 */

@RequestMapping("/admin/file")
@RestController
@Component
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private FileService fileService;

    @Value("${file.dest}")
    private String FILE_DEST;

    @Value("${file.url}")
    private String FILE_URL;

    @RequestMapping("/upload")
    public ResponseModel upload(@RequestParam("file")MultipartFile file,String use) throws IOException {
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));
        FileUseEnum fileUseEnum = FileUseEnum.getByCode(use);
        assert fileUseEnum != null;
        String dir = fileUseEnum.getDesc().toLowerCase();
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf('.')+1).toLowerCase();
        File fullDir = new File(FILE_DEST  +dir);
        if(!fullDir.exists())
        {
            fullDir.mkdir();
        }
        String path = dir + File.separator + UuidUtil.getShortUuid() +"." + suffix;
        file.transferTo(new File(FILE_DEST + path));
        FileModel fileModel = new FileModel();
        fileModel.setPath(FILE_URL + path);
        fileModel.setName(filename);
        fileModel.setUse(use);
        fileModel.setSize(Math.toIntExact(file.getSize()));
        fileModel.setSuffix(suffix);
        fileService.save(fileModel);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(fileModel);
        return responseModel;
    }

}
