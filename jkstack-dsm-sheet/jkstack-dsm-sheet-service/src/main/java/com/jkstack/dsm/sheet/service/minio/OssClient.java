package com.jkstack.dsm.sheet.service.minio;

/**
 * @program: itsm
 * @description: oss客户端
 * @author: DengMin
 * @create: 2020-09-03 14:25
 **/
public abstract class OssClient {

    enum TimeType {
        SECONDS,
        MINUTE,
        HOUR,
        DAY,
    }

    public final static String ContentType_Octet_Stream = "application/octet-stream";
    public final static String ContentType_Image = "image/jpeg";

    public Integer getTime(TimeType type,Integer num){
        switch (type){
            case SECONDS:
                return num;
            case MINUTE:
                return num*60;
            case HOUR:
                return num*60*60;
            case DAY:
                return num*60*60*24;
                default:
                    return 0;
        }
    }
}
