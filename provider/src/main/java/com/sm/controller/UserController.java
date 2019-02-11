package com.sm.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2019/1/7.
 */
@RestController
public class UserController {

    @Value("${server.port}")
    private int Port;

    @GetMapping("/list")
    public List<MoUser> getList() {

        List<MoUser> users = new ArrayList<>();

        IntStream.range(1, 10).forEach(b -> {
            users.add(new MoUser(b, "神牛00" + b + "，端口：" + Port));
        });

        return users;
    }

    class MoUser {

        private int id;
        private String name;

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

