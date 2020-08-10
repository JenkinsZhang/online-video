package com.jenkins.file.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.jenkins.server.enums.FileUseEnum;
import com.jenkins.server.model.FileModel;
import com.jenkins.server.model.ResponseModel;
import com.jenkins.server.service.FileService;
import com.jenkins.server.utils.Base64ToMultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author JenkinsZhang
 * @date 2020/8/10
 */


@RequestMapping("/admin/file")
@RestController
public class OssUploadController {


    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.bucket}")
    private String bucket;

    @Value("${oss.url}")
    private String url;


    @RequestMapping("/oss-append")
    public ResponseModel upload(@RequestBody FileModel fileModel) throws IOException {
        String use = fileModel.getUse();
        String key = fileModel.getKey();
        String suffix = fileModel.getSuffix();
        Integer shardIndex = fileModel.getShardIndex();
        Integer shardSize = fileModel.getShardSize();
        FileUseEnum fileUseEnum = FileUseEnum.getByCode(use);
        assert fileUseEnum != null;
        String dir = fileUseEnum.getDesc().toLowerCase();
        String path =
                new StringBuilder("")
                        .append(dir)
                        .append(File.separator)
                        .append(key).append(".")
                        .append(suffix)
                        .toString();


        MultipartFile multipartFile = Base64ToMultipartFile.base64ToMultipart(fileModel.getShard());
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ObjectMetadata meta = new ObjectMetadata();
        // 指定上传的内容类型。
        meta.setContentType("text/plain");
        // 通过AppendObjectRequest设置多个参数。
        AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucket,path, new ByteArrayInputStream(multipartFile.getBytes()),meta);

        appendObjectRequest.setPosition((long) ((shardIndex - 1) * shardSize));
        AppendObjectResult appendObjectResult = ossClient.appendObject(appendObjectRequest);
        ossClient.shutdown();

        System.out.println(appendObjectResult.getObjectCRC());
        System.out.println(JSONObject.toJSONString(appendObjectResult));

        LOG.info("Starting saving files");
        fileModel.setPath(path);
        fileService.save(fileModel);
        fileModel.setPath(url + path);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setContent(fileModel);
        return responseModel;
    }
}
