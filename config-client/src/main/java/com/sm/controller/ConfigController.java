package com.sm.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/1/9.
 */
@RestController
public class ConfigController {

    @Value("${shenniu.author}")
    private String author;

    @Value("${shenniu.des}")
    private String des;

    @GetMapping("/getPort")
    public String getPort() {
        return "作者：" + author +
                "描述：" + des;
    }
}
