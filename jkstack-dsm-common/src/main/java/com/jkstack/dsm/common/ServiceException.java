package com.jkstack.dsm.common;

/**
 * 业务业务，导致事务回滚。
 * @author lifang
 * @since 2020/10/16
 */
public class ServiceException extends RuntimeException{

    public ServiceException(){

    }

    public ServiceException(String message){
        super(message);
    }

    public ServiceException(String message, Object...args){
        super(String.format(message, args));
    }

}
