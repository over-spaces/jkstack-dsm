package com.jkstack.dms;

import com.jkstack.dsm.common.utils.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author lifang
 * @since 2020/11/2
 */
public class ValidatorUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorUtilTest.class);

    @Test
    public void isPhone(){
        Assert.assertEquals(true, ValidatorUtil.isPhone("010-123456"));
        Assert.assertEquals(false, ValidatorUtil.isPhone("01A-123456"));
        Assert.assertEquals(true, ValidatorUtil.isPhone("15618757540"));
        Assert.assertEquals(false, ValidatorUtil.isPhone("abc34243"));
    }

    @Test
    public void isChineseAndLetter(){
        Assert.assertEquals(true, ValidatorUtil.isChineseAndLetter("李四"));
        Assert.assertEquals(false, ValidatorUtil.isChineseAndLetter("AB  C"));
        Assert.assertEquals(true, ValidatorUtil.isChineseAndLetter("xiaofeng"));
        Assert.assertEquals(true, ValidatorUtil.isChineseAndLetter("蒙奇D路飞"));
        Assert.assertEquals(false, ValidatorUtil.isChineseAndLetter("李4"));
    }

    @Test
    public void isLetterAndDigital(){
        Assert.assertEquals(true, ValidatorUtil.isLetterAndDigital("ASFG"));
        Assert.assertEquals(false, ValidatorUtil.isLetterAndDigital("AB  C"));
        Assert.assertEquals(true, ValidatorUtil.isLetterAndDigital("xiaofeng"));
        Assert.assertEquals(false, ValidatorUtil.isLetterAndDigital("蒙奇D路飞"));
        Assert.assertEquals(false, ValidatorUtil.isLetterAndDigital("李4"));
        Assert.assertEquals(true, ValidatorUtil.isLetterAndDigital("586868"));
        Assert.assertEquals(true, ValidatorUtil.isLetterAndDigital("586868abc"));
        Assert.assertEquals(true, ValidatorUtil.isLetterAndDigital("586868ASC"));
        Assert.assertEquals(true, ValidatorUtil.isLetterAndDigital("586868ASC"));
        Assert.assertEquals(true, ValidatorUtil.isLetterAndDigital("A586868ASC"));
    }

}
