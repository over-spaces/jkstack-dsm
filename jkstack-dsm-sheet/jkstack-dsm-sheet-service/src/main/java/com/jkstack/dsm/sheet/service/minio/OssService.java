package com.jkstack.dsm.sheet.service.minio;

import com.jkstack.dsm.common.utils.StringUtil;
import com.jkstack.dsm.sheet.controller.minio.vo.FileBaseInfo;
import com.jkstack.dsm.sheet.entity.minio.FileData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OssService {

    /**
     * 检查是否为空，为空则设置为默认存储桶
     * @param bucketName
     * @return
     */
    default String checkNull(String bucketName){
        if (StringUtil.isEmpty(bucketName)){
            bucketName = "Default";
        }
        return bucketName;
    }

    /**
     * 上传文件
     */
    FileBaseInfo upload(String bucketName, Boolean bucketIsPublic, Boolean isPublic, MultipartFile file);

    /**
     * 下载文件
     */
    void download(HttpServletRequest request, HttpServletResponse response, String bucketName, String objectName);

    /**
     * 展示图片
     */
    void showImage(HttpServletRequest request, HttpServletResponse response, String bucketName, String objectName);

    /**
     * 删除文件
     */
    void delete(String bucketName, String objectName);

    /**
     * 根据条件删除文件
     */
    void delete(FileData itsmResource);
}
