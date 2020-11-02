package com.jkstack.dsm.user.controller;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.jkstack.dsm.common.utils.ValidatorUtil;
import com.jkstack.dsm.user.controller.vo.UserExcelVO;
import com.jkstack.dsm.user.entity.UserStatusEnum;
import com.jkstack.dsm.user.service.UserService;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author lifang
 * @since 2020/10/22
 */
public class UserExcelImportVerifyHandler implements IExcelVerifyHandler<UserExcelVO> {

    private UserService userService;

    public UserExcelImportVerifyHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ExcelVerifyHandlerResult verifyHandler(UserExcelVO userExcelVO) {
        String message = "";
        if (StringUtils.isBlank(userExcelVO.getLoginName())) {
            message += "用户名不允许为空;";
        }

        boolean check = userService.checkUserLoginName(null, userExcelVO.getLoginName());
        if (!check) {
            message += "用户名已存在;";
        }

        if (StringUtils.isBlank(userExcelVO.getName())) {
            message += "姓名不允许为空;";
        }else if(StringUtils.length(userExcelVO.getName()) > 20 || !ValidatorUtil.isChineseAndLetter(userExcelVO.getName())){
            message += "姓名字符过长,不可超过20个字符，只允许中文字母";
        }

        if (StringUtils.isNotBlank(userExcelVO.getUserNo()) && (StringUtils.length(userExcelVO.getUserNo()) > 50 || !ValidatorUtil.isLetterAndDigital(userExcelVO.getUserNo()))){
            message += "工号字符过长,不可超过50个字符，只允许字母数字";
        }

        if (StringUtils.isBlank(userExcelVO.getPhone())) {
            message += "电话不允许为空;";
        }else if(!ValidatorUtil.isPhone(userExcelVO.getPhone())){
            message = "不可超过20个字符，只能输入数字和符号 ‘-’、‘(’、‘)’;";
        }

        if (StringUtils.isBlank(userExcelVO.getStatusText())) {
            message += "状态不允许为空;";
        }

        boolean flag = Arrays.stream(UserStatusEnum.values())
                .noneMatch(userStatusEnum -> StringUtils.equals(userStatusEnum.getText(), userExcelVO.getStatusText()));
        if (flag) {
            message += "状态不合法;";
        }

        if (StringUtils.isNotBlank(userExcelVO.getEmail()) && !ValidatorUtil.isEmail(userExcelVO.getEmail())) {
            message += "邮箱格式不正确;";
        }
        return new ExcelVerifyHandlerResult(StringUtils.isBlank(message), message);
    }
}
