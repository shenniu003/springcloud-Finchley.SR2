package com.sm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2019/1/12.
 */
@Service
public class UserService  {

    @Autowired
    RestTemplate restTemplate;

//    @Override
//    @HystrixCommand(fallbackMethod = "fallbackMethod")
//    public List<MoUser> getList() {
//        //调用provider服务
//        return restTemplate.getForObject("http://PROVIDER/list", List.class);
//    }
//
//    List<MoUser> fallbackMethod() {
//        return new ArrayList<MoUser>() {
//            {
//                add(new MoUser(1, "服务挂了"));
//            }
//        };
//    }

}
