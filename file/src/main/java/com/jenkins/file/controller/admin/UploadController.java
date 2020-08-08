package com.jenkins.file.controller.admin;

import com.google.inject.internal.cglib.core.$AbstractClassGenerator;
import com.jenkins.server.enums.FileUseEnum;
import com.jenkins.server.model.FileModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.FileService;
import com.jenkins.server.utils.Base64ToMultipartFile;
import com.jenkins.server.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    public ResponseModel upload(@RequestBody FileModel fileModel) throws IOException {
        String use = fileModel.getUse();
        String key = fileModel.getKey();
        String suffix = fileModel.getSuffix();


        FileUseEnum fileUseEnum = FileUseEnum.getByCode(use);
        assert fileUseEnum != null;
        String dir = fileUseEnum.getDesc().toLowerCase();
        File fullDir = new File(FILE_DEST + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }
        String localPath =
                new StringBuilder("")
                .append(dir)
                .append(File.separator)
                .append(key).append(".")
                .append(suffix)
                .toString();
        String filePath =
                new StringBuilder(localPath)
                .append(".")
                .append(fileModel.getShardIndex())
                .toString();
        MultipartFile multipartFile = Base64ToMultipartFile.base64ToMultipart(fileModel.getShard());
        multipartFile.transferTo(new File(FILE_DEST + filePath));
        fileModel.setPath(localPath);
        fileService.save(fileModel);
        fileModel.setPath(FILE_URL + localPath);
        if(fileModel.getShardIndex().equals(fileModel.getShardTotal()))
        {
            merge(fileModel);
        }
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(fileModel);
        return responseModel;
    }

    public void merge(FileModel fileModel) throws IOException {
        LOG.info("Start merging shards...");
        String path = fileModel.getPath().replace(FILE_URL,"");
        path = FILE_DEST + path;
        FileOutputStream fileOutputStream = new FileOutputStream(new File(path),true);
        int len;
        byte[] bytes = new byte[10*1024*1024];
        for(int i = 0 ; i < fileModel.getShardTotal();i++)
        {
            FileInputStream fileInputStream = new FileInputStream(new File(path+"."+(i+1)));
            while((len = fileInputStream.read(bytes)) !=-1)
            {
                fileOutputStream.write(bytes,0,len);
            }
        }
        fileOutputStream.flush();
        LOG.info("Merging completed!");
        LOG.info("Start deleting...");
        System.gc();
        for(int i =0 ; i < fileModel.getShardTotal();i++)
        {
            File file = new File(path+"."+(i+1));
            boolean delete = file.delete();
            LOG.info("Deleting success or not? {}", delete ? "Yes" : "No");
        }
        LOG.info("Deleting completed!");
    }

}
