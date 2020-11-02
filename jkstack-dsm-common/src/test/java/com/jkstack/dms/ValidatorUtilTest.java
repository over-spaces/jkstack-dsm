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
    public void testIsPhone(){
        Assert.assertEquals(true, ValidatorUtil.isPhone("010-123456"));
        Assert.assertEquals(false, ValidatorUtil.isPhone("01A-123456"));
        Assert.assertEquals(true, ValidatorUtil.isPhone("15618757540"));
        Assert.assertEquals(false, ValidatorUtil.isPhone("abc34243"));
        Assert.assertEquals(false, ValidatorUtil.isPhone("abc34243111111111111111111111111111111111111111111111111"));
    }

    @Test
    public void testIsName(){
        Assert.assertEquals(true, ValidatorUtil.isName("李四"));
        Assert.assertEquals(false, ValidatorUtil.isName("AB  C"));
        Assert.assertEquals(true, ValidatorUtil.isName("xiaofeng"));
        Assert.assertEquals(true, ValidatorUtil.isName("蒙奇D路飞"));
        Assert.assertEquals(false, ValidatorUtil.isName("李4"));
        Assert.assertEquals(false, ValidatorUtil.isName("abc34243111111111111111111111111111111111111111111111111"));
    }

}
