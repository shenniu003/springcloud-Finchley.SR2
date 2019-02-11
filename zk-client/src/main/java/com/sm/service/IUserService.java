package com.sm.service;

import com.sm.domain.MoUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Created by Administrator on 2019/1/26.
 */
@Service
@FeignClient("zk-server")
public interface IUserService {
    @GetMapping("/list")
    List<MoUser> getList();
}
