package com.jkstack.dsm.entity;

import lombok.Data;

@Data
public class UserEntity extends BaseEntity{

    private String name;

    private String password;
}
