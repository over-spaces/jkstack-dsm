package com.jkstack.dsm.entity;

import com.jkstack.dsm.common.BaseEntity;
import lombok.Data;

@Data
public class UserEntity extends BaseEntity {

    private String name;

    private String password;
}
