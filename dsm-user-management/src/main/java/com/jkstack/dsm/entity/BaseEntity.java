package com.jkstack.dsm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    private long id;

    private Date createdDate;

    private Date modifyDate;

}
