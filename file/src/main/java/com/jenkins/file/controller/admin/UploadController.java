package com.jenkins.file.controller.admin;

import com.google.inject.internal.cglib.core.$AbstractClassGenerator;
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
    public ResponseModel upload(@RequestParam("file")MultipartFile shard,
                                String use,
                                String name,
                                String suffix,
                                Integer shardIndex,
                                Integer shardSize,
                                Integer size,
                                Integer shardTotal

    ) throws IOException {
        FileUseEnum fileUseEnum = FileUseEnum.getByCode(use);
        assert fileUseEnum != null;
        String dir = fileUseEnum.getDesc().toLowerCase();
        String key = UuidUtil.getShortUuid();
        File fullDir = new File(FILE_DEST  +dir);
        if(!fullDir.exists())
        {
            fullDir.mkdir();
        }
        String path = dir + File.separator + key +"." + suffix;
        shard.transferTo(new File(FILE_DEST + path));
        FileModel fileModel = new FileModel();
        fileModel.setPath(path);
        fileModel.setName(name);
        fileModel.setUse(use);
        fileModel.setSize(size);
        fileModel.setSuffix(suffix);
        fileModel.setShardIndex(shardIndex);
        fileModel.setShardSize(shardSize);
        fileModel.setShardTotal(shardTotal);
        fileModel.setKey(key);
        fileService.save(fileModel);
        fileModel.setPath(FILE_URL + path);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(fileModel);
        return responseModel;
    }

    @RequestMapping("/merge")
    public ResponseModel merge() throws IOException
    {
        File inputFile1 = new File("xxxx");
        FileInputStream inputStream = new FileInputStream(inputFile1);
        int len;
        File outputFile = new File("xxxx");
        FileOutputStream outputStream = new FileOutputStream(outputFile,true);
        byte[] bytes = new byte[10*1024*1024];
        while((len=inputStream.read(bytes)) != -1)
        {
            outputStream.write(bytes,0,len);
            outputStream.flush();
        }

        ResponseModel responseModel = new ResponseModel();
        return responseModel;

    }

}
