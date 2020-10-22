package com.jkstack.dsm.user.controller.vo;

import com.jkstack.dsm.user.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户列表
 * @author lifang
 * @since 2020/10/21
 */
@Setter
@Getter
@ApiModel
@NoArgsConstructor
public class UserListVO extends UserVO{

    public UserListVO(UserEntity userEntity){
        super(userEntity);
    }
}
