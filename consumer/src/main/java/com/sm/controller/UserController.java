package com.sm.controller;

import com.sm.domain.MoUser;
import com.sm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/8.
 */
@RestController
public class UserController {

    @Autowired
    IUserService userService;

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    DiscoveryClient client;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/list00")
    public List<MoUser> getList0() {

        List<MoUser> list = new ArrayList<>();

        client.getInstances("PROVIDER").forEach(b -> {

            System.out.println(b.getUri().toString() + "|" + b.getServiceId());

            List<MoUser> users = restTemplate.getForObject(b.getUri().toString() + "/list", List.class);

            list.addAll(users);

        });

        return list;
    }

    @GetMapping("/list01")
    public List<MoUser> getList01() {

        //List<MoUser> list = restTemplate.getForObject("http://PROVIDER/list", List.class);

        return userService.getList();
    }

    @GetMapping("/list02")
    public List<MoUser> getList2() {
        return userService.getList();
    }

}

