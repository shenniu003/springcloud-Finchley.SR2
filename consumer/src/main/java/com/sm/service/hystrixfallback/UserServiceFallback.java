package com.sm.service.hystrixfallback;

import com.sm.domain.MoUser;
import com.sm.service.IUserService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/9.
 */
@Component
public class UserServiceFallback implements FallbackFactory<IUserService> {

    @Override
    public IUserService create(Throwable throwable) {
        return new IUserService() {
            @Override
            public List<MoUser> getList() {
                return new ArrayList<MoUser>() {
                    {
                        add(new MoUser(0, "神牛-fallback：" +
                                throwable.toString()));
                    }
                };
            }
        };
    }
}
