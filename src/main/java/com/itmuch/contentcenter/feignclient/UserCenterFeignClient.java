package com.itmuch.contentcenter.feignclient;

import com.itmuch.contentcenter.configration.UserCenterFeignConfigration;
import com.itmuch.contentcenter.domain.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-center",configuration = UserCenterFeignConfigration.class)
public interface UserCenterFeignClient {
    /**
     * 声明式 http 客户端 name 表示的请求的应用
     * feign 请求 user-center 应用服务
     * http://user-center/users/{id}
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable Integer id);
}
