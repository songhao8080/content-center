package com.itmuch.contentcenter.configration;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 让feign
 * 禁止使用 //@Configuration
 * 因为会产生父子上下文问题，
 * 如果想使用，可以把它挪到 @componnetScan 无法扫描到的地方
 *
 * 让 feign 打印所有细节
 */

public class UserCenterFeignConfigration {
    @Bean
    public Logger.Level level(){

        return Logger.Level.FULL;
    }
}
