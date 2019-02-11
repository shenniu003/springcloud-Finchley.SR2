package com.sm.service;

import com.sm.domain.MoUser;
import com.sm.service.hystrixfallback.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "PROVIDER",fallbackFactory = UserServiceFallback.class)
public interface IUserService {
    @GetMapping("/list")
    List<MoUser> getList();
}
