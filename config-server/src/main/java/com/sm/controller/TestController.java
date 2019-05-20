package com.sm.controller;

import org.apache.log4j.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/4/22.
 */
@RestController
public class TestController {

    static Logger logger = Logger.getLogger(TestController.class);

    @GetMapping("/hello/{nickname}")
    public String getHello(@PathVariable String nickname) {
        String str = String.format("你好，%s", nickname);
        logger.debug(str);
        logger.info(str);
        logger.error(str);
        return str;
    }

}
