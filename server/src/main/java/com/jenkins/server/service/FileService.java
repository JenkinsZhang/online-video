package com.jenkins.server.service;
import java.util.Date;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jenkins.server.entity.File;
import com.jenkins.server.entity.FileExample;
import com.jenkins.server.mapper.FileMapper;
import com.jenkins.server.model.FileModel;
import com.jenkins.server.model.PageModel;
import com.jenkins.server.utils.CopyUtil;
import com.jenkins.server.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/10
 */
@Service
public class FileService {

    private FileMapper fileMapper;

    @Autowired
    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void fileList(PageModel pageModel)
    {
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        FileExample fileExample = new FileExample();
        fileExample.setOrderByClause("created_at desc");
        List<File> fileList = fileMapper.selectByExample(fileExample);
        PageInfo<File> pageInfo = new PageInfo<>(fileList);
        pageModel.setTotal(pageInfo.getTotal());
        List<FileModel> fileModelList = CopyUtil.copyList(fileList, FileModel.class);
        pageModel.setList(fileModelList);

    }

    public FileModel save(FileModel fileModel)
    {
        File file = selectByKey(fileModel);
        if(file == null)
        {
            insert(fileModel);
        }
        else {
            file.setShardIndex(fileModel.getShardIndex());
            FileModel copy = CopyUtil.copy(file, FileModel.class);
            update(copy);
        }
        return fileModel;
    }

    public void update(FileModel fileModel)
    {
        File copy = CopyUtil.copy(fileModel, File.class);
        Date now  = new Date();
        copy.setUpdatedAt(now);
        this.fileMapper.updateByPrimaryKey(copy);
    }

    public void insert(FileModel fileModel)
    {

        File copy = CopyUtil.copy(fileModel,File.class);
        Date now  = new Date();
        copy.setCreatedAt(now);
        copy.setUpdatedAt(now);
        copy.setId(UuidUtil.getShortUuid());
        this.fileMapper.insert(copy);
    }

    public void delete(String id)
    {
        fileMapper.deleteByPrimaryKey(id);
    }

    public File selectByKey(FileModel fileModel)
    {
        String key = fileModel.getKey();
        FileExample fileExample  = new FileExample();
        fileExample.createCriteria().andKeyEqualTo(key);
        List<File> files = fileMapper.selectByExample(fileExample);
        if(CollectionUtils.isEmpty(files))
        {
            return null;
        }
        return files.get(0);
    }

    public FileModel check(String key)
    {
        FileModel fileModel = new FileModel();
        fileModel.setKey(key);
        File file = selectByKey(fileModel);
        return CopyUtil.copy(file,FileModel.class);
    }
}