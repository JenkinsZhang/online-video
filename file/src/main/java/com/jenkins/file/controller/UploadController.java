package com.jenkins.file.controller;

import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RequestMapping("/admin")
@RestController
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);


    @RequestMapping("/upload")
    public ResponseModel upload(@RequestParam("file")MultipartFile file) throws IOException {
        LOG.info("Begin uploading file {}",file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        String filename = file.getOriginalFilename();
        String key = UuidUtil.getShortUuid();
        String dest = "/Users/jenkinszhang/Jobs/LinkedInProjects/onlineVideo/Saved_Files/avatars/" + key+ "_"+filename;
        File fileDest = new File(dest);
        file.transferTo(fileDest);

        return new ResponseModel();
    }

}
