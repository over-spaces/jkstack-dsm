package com.jkstack.dsm;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
