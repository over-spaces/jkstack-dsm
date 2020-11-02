package com.jkstack.dsm.sheet.service.minio;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.Result;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.Item;
import io.minio.messages.Upload;
import io.minio.policy.PolicyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: itsm
 * @description:
 * @author: DengMin
 * @create: 2020-09-03 11:39
 **/
@Slf4j
@Component
public class MinioClientUtil extends OssClient {

    private MinioClient minioClient;

    private String getEndpoint;
    
    public String getEndpoint() {
        return this.getEndpoint;
    }

    public MinioClientUtil(@Autowired MinioConf minioConf) {
        try {
            getEndpoint = minioConf.getEndpoint();
            if (minioConf.getIsOpen()){
                this.minioClient = new MinioClient(minioConf.getEndpoint(), minioConf.getAccessKey(), minioConf.getSecretKey());
                log.info(">>>>>>>>>>>>minio已开启");
            }else {
                log.warn(">>>>>>>>>>>>minio未开启");
            }
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
            throw new RuntimeException("minio:无效地址");
        } catch (InvalidPortException e) {
            e.printStackTrace();
            throw new RuntimeException("minio:无效端口");
        }
    }

    /**
     * 创建桶
     * @param bucket 桶名称
     */
   private void makeBucket(String bucket,Boolean isPublic){
       boolean isExist = false;
       try {
           isExist = minioClient.bucketExists(bucket);
           if(isExist) {
               log.info("Bucket already exists.");
           } else {
               minioClient.makeBucket(bucket);
               if (isPublic){
                   minioClient.setBucketPolicy(bucket,"*", PolicyType.READ_ONLY);
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException("创建存储桶失败");
       }
    }

    /**
     * 列出所有的存储桶信息
     * @return
     */
    public List<Bucket> listBuckets() {
        try{
            return minioClient.listBuckets();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("获取所有桶异常");
        }
    }

    /**
     * 删除存储桶数据
     * @param bucketName
     */
    public void removeBucket(String bucketName) {
        try{
            // 删除之前先检查是否存在。
            boolean found = minioClient.bucketExists(bucketName);
            if (found) {
                // 删除存储桶，注意，只有存储桶为空时才能删除成功。
                minioClient.removeBucket(bucketName);
                log.info("{} is removed successfully",bucketName);
            } else {
                log.info("{} does not exist",bucketName);
            }
        }catch (Exception e){
            e.printStackTrace();
           throw new RuntimeException("删除存储桶失败");
        }
    }

    /**
     * 获取某个存储桶中所有的对象
     * @param bucketName
     * @param prefix
     * @param recursive
     * @param useVersion1
     * @return
     */
    public List<Item> listObjects(String bucketName, String prefix, boolean recursive, boolean useVersion1){
        try{
            // 检查是否存在。
            boolean found = minioClient.bucketExists(bucketName);
            List<Item> items = new ArrayList<>();
            if (found) {
                // 列出'bucketname'里的对象
                Iterable<Result<Item>> myObjects = minioClient.listObjects(bucketName);
                for (Result<Item> result : myObjects) {
                    Item item = result.get();
                    items.add(item);
                }
            } else {
                log.info("{} does not exist",bucketName);
            }
            return items;
        }catch (Exception e){
            throw new RuntimeException("获取存储桶失败");
        }
    }

    /**
     * 查看存储桶中所有没有被上传完成的对象
     * @param bucketName
     * @param prefix
     * @param recursive
     * @return
     */
    public List<Upload> listIncompleteUploads(String bucketName, String prefix, boolean recursive) {
        try{
            // 检查是否存在。
            boolean found = minioClient.bucketExists(bucketName);
            List<Upload> uploads = new ArrayList<>();
            if (found) {
                // 列出存储桶中所有未完成的multipart上传的的对象。
                Iterable<Result<Upload>> myObjects = minioClient.listIncompleteUploads(bucketName);
                for (Result<Upload> result : myObjects) {
                    Upload upload = result.get();
                    uploads.add(upload);
                }
            } else {
                log.info("{} does not exist",bucketName);
            }
            return uploads;
        }catch (Exception e){
            log.error("查看存储桶中所有没有被上传完成的对象时异常:{}",e.getMessage());
           throw new RuntimeException("获取失败");
        }
    }


    /**
     * 获得指定对象前缀的存储桶策略。
     * @param bucketName 存储桶名称。
     * @param objectPrefix  策略适用的对象的前缀
     * @return
     */
    public PolicyType getBucketPolicy(String bucketName, String objectPrefix) {
        try{
            return minioClient.getBucketPolicy(bucketName, objectPrefix);
        }
        catch (Exception e){
            log.error("获得指定对象前缀的存储桶策略时异常:{}",e.getMessage());
            throw new RuntimeException("获取失败");
        }
    }

    /**
     * 给一个存储桶+对象前缀设置策略。
     * @param bucketName
     * @param objectPrefix
     * @param policy
     */
    public void setBucketPolicy(String bucketName, String objectPrefix, PolicyType policy) {
        try{
            minioClient.setBucketPolicy(bucketName, objectPrefix, policy);
        }catch (Exception e){
            log.error("给一个存储桶+对象前缀设置策略时异常:{}",e.getMessage());
            throw new RuntimeException("设置失败");
        }
    }

    /**
     * 以流的形式下载一个对象。
     * @param bucketName 存储桶名称。
     * @param objectName 存储桶里的对象名称。
     * @return 返回流，使用完毕后记得关闭。
     */
    public InputStream getObject(String bucketName, String objectName) {
        // 调用statObject()来判断对象是否存在。
        // 如果不存在, statObject()抛出异常,
        // 否则则代表对象存在。
        InputStream stream = null;
        try {
            minioClient.statObject(bucketName, objectName);
        // 获取的输入流。
         stream = minioClient.getObject(bucketName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("minio 下载异常:{}",e.getMessage());
            throw new RuntimeException("文件下载失败:"+e.getMessage());

        }
        return stream;
    }

    /**
     * 下载对象指定区域的字节数组做为流。（断点下载）
     * @param bucketName 存储桶名称。
     * @param objectName 存储桶里的对象名称。
     * @param offset offset 是起始字节的位置
     * @param length length是要读取的长度 (可选，如果无值则代表读到文件结尾)。
     * @return
     */
    public InputStream getObject(String bucketName, String objectName, long offset, Long length){
        InputStream stream = null;
        try{
            // 调用statObject()来判断对象是否存在。
            // 如果不存在, statObject()抛出异常,
            // 否则则代表对象存在。
            minioClient.statObject("mybucket", "myobject");
            // 获取指定offset和length的"myobject"的输入流。
            stream = minioClient.getObject("mybucket", "myobject", 1024L, 4096L);
            // 读取输入流直到EOF并打印到控制台。
            byte[] buf = new byte[16384];
            int bytesRead;
            while ((bytesRead = stream.read(buf, 0, buf.length)) >= 0) {
                System.out.println(new String(buf, 0, bytesRead));
            }
            // 关闭流。
        }catch (Exception e){
            e.printStackTrace();
            log.error("minio 下载异常:{}",e.getMessage());
            throw new RuntimeException("文件下载异常");
        }finally {
            if (stream!=null){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stream;
    }

    /**
     * 下载文件到本地
     * @param bucketName 存储桶名称。
     * @param objectName 存储桶里的对象名称。
     * @param fileName   文件名称
     */
    public void getObject(String bucketName, String objectName, String fileName) {
        try{
            // 调用statObject()来判断对象是否存在。
            // 如果不存在, statObject()抛出异常,
            // 否则则代表对象存在。
            minioClient.statObject(bucketName, objectName);

            // 获取myobject的流并保存到photo.jpg文件中。
            minioClient.getObject(bucketName, objectName, fileName);
        }catch (Exception e){
            log.error("下载文件到本地时异常:{}",e.getMessage());
            throw new RuntimeException("下载失败");
        }
    }


    /**
     *
     * @param bucketName 存储桶名称。
     * @param objectName 存储桶里的对象名称。
     * @param stream 要上传的流。
     * @param contentType  Content type。
     */
    public void putObject(String bucketName,Boolean bucketIsPublic, String objectName, InputStream stream, String contentType){
        try{
            // 创建对象
            this.makeBucket(bucketName,bucketIsPublic);
            minioClient.putObject(bucketName, objectName, stream, stream.available(), contentType);
            log.info("{}:{} is uploaded successfully",bucketName,objectName);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("上传失败");
        }finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 上传本地文件
     * @param bucketName 存储桶名称。
     * @param objectName 存储桶里的对象名称。
     * @param fileName 文件名称
     */
    public void putObject(String bucketName, String objectName, String fileName){
        try {
            minioClient.putObject(bucketName,  objectName, fileName);
            log.info("{},{} is uploaded successfully",bucketName,objectName);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("文件上传异常");
        }
    }


    /**
     * 获取对象的元数据
     * @param bucketName 存储桶名称。
     * @param objectName 存储桶里的对象名称。
     * @return
     */
    public ObjectStat statObject(String bucketName, String objectName){
        // 获得对象的元数据。
        try{
            return minioClient.statObject(bucketName, objectName);
        }catch (Exception e){
        throw new RuntimeException("获取元数据失败");
        }
    }

    /**
     * 批量删除对象
     * @param bucketName 存储桶名称。
     * @param objectNames 存储桶里的对象名称。
     * @return
     */
    public List<DeleteError> removeObject(String bucketName, List<String> objectNames){

        List<DeleteError> list = new ArrayList<>();
        try {
            // 删除bucketname里的多个对象
            Iterable<Result<DeleteError>> deleteErrorResult = minioClient.removeObject(bucketName, objectNames);
            for (Result<DeleteError> errorResult: deleteErrorResult) {
                DeleteError error = errorResult.get();
                list.add(error);
                log.error("Failed to remove '{}'. Error:{}",error.objectName(), error.message());
            }
            return list;
        }   catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("删除失败");
        }
    }

    /**
     * 删除单个存储对象
     * @param bucketName 存储桶名称。
     * @param objectName 存储桶里的对象名称。
     */
    public void removeObject(String bucketName, String objectName){

        try {
            // 删除bucketname里的多个对象
            minioClient.removeObject(bucketName, objectName);
        }   catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("删除失败");
        }
    }

    /**
     *  删除一个未完整上传的对象。
     * @param bucketName 存储桶名称。
     * @param objectName 存储桶里的对象名称。
     */
    public void removeIncompleteUpload(String bucketName, String objectName){
        // 从存储桶中删除名为myobject的未完整上传的对象。
        try {
            minioClient.removeIncompleteUpload(bucketName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除失败");
        }
        log.info("successfully removed all incomplete upload session of {}:{}",bucketName,objectName);
    }


    /**
     * 生成一个给HTTP GET请求用的presigned URL。浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
     * @param bucketName 存储桶名称。
     * @param objectName 存储桶里的对象名称。
     * @param timeType   时间类型
     * @param expires    失效时间，默认是7天，不得大于七天。
     * @return
     */
    public String presignedGetObject(String bucketName, String objectName,TimeType timeType, Integer expires){
        try {
            return minioClient.presignedGetObject(bucketName, objectName, getTime(timeType,expires));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("获取分析链接失败");
        }
    }
}
