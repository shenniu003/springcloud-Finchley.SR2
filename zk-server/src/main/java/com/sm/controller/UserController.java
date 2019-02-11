package com.sm.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/26.
 */
@RestController
public class UserController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/list")
    public List<MoUser> getList() {

        return new ArrayList<MoUser>() {
            {
                add(new MoUser(1, "shenniu001_" + port));
                add(new MoUser(2, "shenniu002_" + port));
                add(new MoUser(3, "shenniu003_" + port));
            }
        };
    }

    class MoUser {

        private int id;
        private String name;

        public MoUser() {
        }

        public MoUser(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
