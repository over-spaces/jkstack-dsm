package com.jkstack.dsm.sheet.controller.minio;

import com.jkstack.dsm.common.ResponseResult;
import com.jkstack.dsm.sheet.controller.minio.vo.FileBaseInfo;
import com.jkstack.dsm.sheet.service.minio.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: jkstack-dsm
 * @description: 文件控制层
 * @author: DengMin
 * @create: 2020-11-02 14:08
 **/
@RestController
@RequestMapping("/file")
@Api(value = "OSS资源文件处理接口", tags = {"OSS资源文件处理接口"})
public class FileOssController {

    @Autowired
    private OssService ossService;

    private static final String SHOW_IMAGE_URI = "/show/image/";

    public static final String SHOW_IMAGE_URL="/itsmboot/oss"+SHOW_IMAGE_URI;

    @GetMapping("/download/{folder:.+}/{fileName:.+}")
    @ApiOperation("下载附件")
    public void download(@PathVariable String folder, @PathVariable String fileName, HttpServletRequest request, HttpServletResponse response){
        ossService.download(request,response,folder,fileName);
    }

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public ResponseResult upload(@RequestParam("folder") String bucketName, @RequestParam("isPublic") Boolean isPublic, @RequestParam("file") MultipartFile file) {
        FileBaseInfo fileBaseInfo = ossService.upload(bucketName,false,isPublic,file);
        return ResponseResult.success(fileBaseInfo);
    }

    @GetMapping(SHOW_IMAGE_URI+"{folder:.+}/{fileName:.+}")
    @ApiOperation("展示图片")
    public void showImage(@PathVariable String folder,@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response){
        ossService.showImage(request,response,folder,fileName);
    }

}
