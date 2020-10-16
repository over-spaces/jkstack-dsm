package com.jkstack.dsm.common.vo;

import java.io.Serializable;

/**
 * @author lifang
 * @since 2020/10/15
 */
public class SimpleDataVO implements Serializable {

    private String id;

    private String name;

    public SimpleDataVO() {
    }

    public SimpleDataVO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
