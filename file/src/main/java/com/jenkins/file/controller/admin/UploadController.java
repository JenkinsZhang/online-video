package com.jenkins.file.controller.admin;

import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Value("${file.dest}")
    private String FILE_DEST;

    @Value("${file.url}")
    private String FILE_URL;

    @RequestMapping("/upload")
    public ResponseModel upload(@RequestParam("file")MultipartFile file) throws IOException {
        LOG.info("Begin uploading file {}",file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        String filename = file.getOriginalFilename();
        String key = UuidUtil.getShortUuid();
        String dest = FILE_DEST + "teacher/avatars/" + key+ "_"+filename;
        System.out.println(dest);
        File fileDest = new File(dest);
        file.transferTo(fileDest);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent( FILE_URL +"teacher/avatars/"+key+"_"+filename);
        return responseModel;
    }

}
