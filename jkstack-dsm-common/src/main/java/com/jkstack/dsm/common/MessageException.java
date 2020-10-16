package com.jkstack.dsm.common;

/**
 * @author lifang
 * @since 2020/10/16
 */
public class MessageException extends Exception{

    public MessageException(){

    }

    public MessageException(String message){
        super(message);
    }

    public MessageException(String message, Object...args){
        super(String.format(message, args));
    }
}
