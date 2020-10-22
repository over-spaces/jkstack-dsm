package com.jkstack.dsm.common;

import com.jkstack.dsm.common.utils.StringUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 控制基础类，所有controller都应该继承这个类
 */
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    public void download(String filePath, String fileName, HttpServletResponse response) {
        try {
            File file = new File(filePath);
            download(fileName, file, response);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void download(String filePath, HttpServletResponse response) {
        try {
            File file = new File(filePath);
            download(file.getName(), file, response);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    public void downloadExcel(String fileName, Workbook workbook, HttpServletResponse response){
        try(OutputStream out = response.getOutputStream()) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void download(String fileName, File file, HttpServletResponse response) throws UnsupportedEncodingException {
        // 文件是否存在
        if (!file.exists()) {
            logger.error("下载的文件不存在，文件路径：{}", file.getAbsolutePath());
        }

        response.setContentType(getContentType(file));
        response.addHeader("content-type", "application/x-msdownload");
        response.setCharacterEncoding("UTF-8");

        // 重置文件
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));

        byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);
             OutputStream os = response.getOutputStream()) {

            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer);
                i = bis.read(buffer);
            }
            os.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String getContentType(File file){
        String fileName = file.getName();
        if(StringUtil.endsWithIgnoreCaseAny(fileName, ".xls", ".xlsx")){
            return "application/vnd.ms-excel";
        }
        else if(StringUtil.endsWithIgnoreCaseAny(fileName, ".zip")){
            return "application/x-zip-compressed";
        }
        return "application/octet-stream";
    }
}