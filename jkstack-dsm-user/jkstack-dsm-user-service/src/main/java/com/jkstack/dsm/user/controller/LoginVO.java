package com.jkstack.dsm.user.controller;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lifang
 * @since 2020/10/10
 */
@Data
public class LoginVO implements Serializable {

    private String token;

}
