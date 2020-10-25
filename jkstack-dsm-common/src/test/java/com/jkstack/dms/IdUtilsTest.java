package com.jkstack.dms;

import cn.hutool.core.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class IdUtilsTest {

    private static Logger logger = LoggerFactory.getLogger(IdUtilsTest.class);


    @Test
    public void testObjectId(){

        String objectId = IdUtil.objectId();

        logger.info("objectId:{}", objectId);
    }

}
