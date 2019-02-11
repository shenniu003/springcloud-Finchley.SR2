package com.sm.controller;

import com.sm.domain.MoUser;
import com.sm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2019/1/26.
 */
@RestController
public class UserController {

    @Autowired
    protected IUserService userService;

    @GetMapping("/list")
    public List<MoUser> getList() {
        return userService.getList();
    }
}
