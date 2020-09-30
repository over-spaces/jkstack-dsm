package com.jkstack.dsm.user.entity;

import lombok.Data;

@Data
public class UserEntity extends BaseEntity{

    private String name;

    private String password;
}
