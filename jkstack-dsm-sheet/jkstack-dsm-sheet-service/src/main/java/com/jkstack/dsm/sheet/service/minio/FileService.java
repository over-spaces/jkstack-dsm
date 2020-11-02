package com.jkstack.dsm.sheet.service.minio;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jkstack.dsm.common.service.CommonServiceImpl;
import com.jkstack.dsm.sheet.entity.minio.FileData;
import com.jkstack.dsm.sheet.enums.FileTypeEnum;
import com.jkstack.dsm.sheet.mapper.minio.FileDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @program: itsm
 * @description:
 * @author: DengMin
 * @create: 2020-09-03 15:52
 **/
@Slf4j
public abstract class FileService extends CommonServiceImpl<FileDataMapper,FileData> {

    /**
     *   保存文件基本信息
     * @param bucketName 存储桶
     * @param objectName oss对象
     * @param sourceName 原文件名称
     * @return 文件地址
     */
    @Transactional
    protected String saveFileInfo(String bucketName,String objectName,String sourceName){
        String resourceUrl = "/"+bucketName+"/"+objectName;
        FileData itsmResource = FileData.builder()
                .fileName(sourceName)
                .fileUrl(resourceUrl)
                .fileSuffix(this.getSuffix(sourceName))
                .fileFolder(bucketName).ossName(objectName)
                .fileType(this.getFileType(sourceName)).build();
        this.save(itsmResource);
        return itsmResource.getFileUrl();
    }

    private FileTypeEnum getFileType(String sourceName){
        if (this.checkImage(sourceName)){
            return FileTypeEnum.IMAGE;
        }else {
            return FileTypeEnum.FILE;
        }
    }


    /**
     * 查询文件基本信息
     * @param bucketName 存储桶
     * @param objectName oss对象名称
     * @return 文件信息
     */
    protected FileData findFileInfo(String bucketName,String objectName){
        FileData fileData = this.getOne(Wrappers.<FileData>lambdaQuery()
                .eq(FileData::getFileFolder,bucketName)
                .eq(FileData::getOssName,objectName));
        if (fileData == null){
            throw new RuntimeException("文件不存在");
        }
        return fileData;
    }


    /**
     * 检查是否为图片
     * @param sourceName 文件名称
     * @return boolean
     */
    protected boolean checkImage(String sourceName){
       String suffix = this.getSuffix(sourceName);
       List<String> list = Arrays.asList("jpg","png","gif","jpeg");
       return list.contains(suffix);
    }

    /**
     * 获取文件后缀
     * @param obj 文件名称
     * @return 文件后缀
     */
    protected String getSuffix(String obj){
       return obj.substring(obj.indexOf(".")+1);
    }

    /**
     * 处理下载请求
     * @param response 返回请求
     * @param resourceName 文件名称
     * @param stream 文件流
     */
    protected void downloadByResponse(HttpServletResponse response, String resourceName, InputStream stream){
        try {
            byte buf[] = new byte[1024];
            int length = 0;
            response.reset();
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(resourceName.getBytes("gb2312"), "ISO8859-1"));
            OutputStream os = response.getOutputStream();

            while ((length = stream.read(buf)) > 0) {
                os.write(buf, 0, length);
            }
            os.close();
            os.flush();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 图片展示
     * @param request
     * @param response
     * @param stream
     */
    public void showImage(HttpServletRequest request, HttpServletResponse response, InputStream stream, String suffix){
        try {
            byte buf[] = new byte[1024];
            int length = 0;
            response.reset();
            // 设置强制下载不打开
            response.setContentType("image/"+suffix);
            // 设置文件名
            OutputStream os = response.getOutputStream();
            while ((length = stream.read(buf)) > 0) {
                os.write(buf, 0, length);
            }
            os.close();
            os.flush();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
