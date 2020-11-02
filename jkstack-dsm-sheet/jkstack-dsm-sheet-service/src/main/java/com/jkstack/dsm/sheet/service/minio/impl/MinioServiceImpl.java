package com.jkstack.dsm.sheet.service.minio.impl;

import com.jkstack.dsm.sheet.controller.minio.vo.FileBaseInfo;
import com.jkstack.dsm.sheet.entity.minio.FileData;
import com.jkstack.dsm.sheet.service.minio.FileService;
import com.jkstack.dsm.sheet.service.minio.MinioClientUtil;
import com.jkstack.dsm.sheet.service.minio.OssClient;
import com.jkstack.dsm.sheet.service.minio.OssService;
import io.minio.policy.PolicyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @program: itsm
 * @description: minio存储
 * @author: DengMin
 * @create: 2020-09-03 11:35
 **/
@Component
@Slf4j
public class MinioServiceImpl extends FileService implements OssService {

    @Autowired
    private MinioClientUtil ossClient;

    @Override
    public FileBaseInfo upload(String bucketName, Boolean bucketIsPublic, Boolean isPublic, MultipartFile file) {
        //如果没有指定存储桶，则为默认
        bucketName = checkNull(bucketName);
        String sourceFileName = file.getOriginalFilename();
        String objectName = UUID.randomUUID().toString()+"."+getSuffix(sourceFileName);
        try {
            //上传文件
            ossClient.putObject(bucketName,bucketIsPublic,objectName,file.getInputStream(),checkImage(sourceFileName)? OssClient.ContentType_Image:OssClient.ContentType_Octet_Stream);
            if (isPublic){
                if (!bucketIsPublic) {
                    //设置存储对象公开策略 如果存储桶已设置，则对象可以不用再设置
                    ossClient.setBucketPolicy(bucketName,objectName, PolicyType.READ_ONLY);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传异常");
        }
        //获取文件访问uri路径
        String url = saveFileInfo(bucketName,objectName,sourceFileName);
        return FileBaseInfo.builder().name(objectName).bucket(bucketName).addr(isPublic?ossClient.getEndpoint()+url:"").isPublic(isPublic).build();
    }

    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, String bucketName, String objectName) {
        //如果没有指定存储桶，则为默认
        bucketName = checkNull(bucketName);
        //获取库中文件信息
        FileData fileData = super.findFileInfo(bucketName, objectName);
        //获取流文件
        InputStream inputStream = ossClient.getObject(bucketName,objectName);
        //下载文件，并关闭流
        downloadByResponse(response,fileData.getFileName(),inputStream);
    }

    @Override
    public void showImage(HttpServletRequest request, HttpServletResponse response, String bucketName, String objectName) {
        //如果没有指定存储桶，则为默认
        bucketName = checkNull(bucketName);
        //获取库中文件信息
        FileData fileData = super.findFileInfo(bucketName, objectName);
        //获取流文件
        InputStream inputStream = ossClient.getObject(bucketName,objectName);
        //下载文件，并关闭流
        showImage(request,response,inputStream,fileData.getFileSuffix());
    }

    @Override
    public void delete(String bucketName, String objectName) {
        //如果没有指定存储桶，则为默认
        bucketName = checkNull(bucketName);
        ossClient.removeObject(bucketName,objectName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(FileData fileData) {
        if (fileData!=null){
            FileData check = this.getByBusinessId(fileData.getFileId());
            if (check!=null){
                this.removeByBusinessId(check.getFileId());
                //删除OSS文件
                this.delete(check.getFileFolder(),check.getOssName());
            }
        }
    }
}
