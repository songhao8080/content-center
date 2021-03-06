package com.itmuch.contentcenter.service.content;

import com.alibaba.fastjson.JSONObject;
import com.itmuch.contentcenter.dao.content.ShareMapper;
import com.itmuch.contentcenter.domain.dto.content.ShareDTO;
import com.itmuch.contentcenter.domain.dto.user.UserDTO;
import com.itmuch.contentcenter.domain.entity.content.Share;
import com.itmuch.contentcenter.feignclient.UserCenterFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShareService {
    @Resource
    private ShareMapper shareMapper;

    @Autowired
    private UserCenterFeignClient userCenterFeignClient;



    public ShareDTO findById(Integer id) {


        Share share = shareMapper.selectByPrimaryKey(id);
        log.info(share.toString());


        /**
         * 获取用户中心所有的实例地址
         */
        /**
         *
         * // 获取用户中心所有实例
         *         List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
         @Resource
         private DiscoveryClient discoveryClient;
        List<String> targetUrls = instances.stream()
                .map(instance -> instance.getUri().toString() + "/users/{id}")
                .collect(Collectors.toList());

        log.info("所有的实例 {}", targetUrls.toArray());
        int randomInt = ThreadLocalRandom.current().nextInt(targetUrls.size());

        String targetUrl = targetUrls.get(randomInt);
        log.info("获取的 TargetUrl: {}", targetUrl);
         Integer userId = share.getUserId();

        log.info("userId {}", userId);
         */

        /**
         * 加入 Ribbon
         * 1. 在 restTemplate 加入 @LoadBalanced，这样ribbon就可以使用负载均衡
         * 2. "http://user-center/users/{id}" Ribbon 会解析 user-center，调用的是Nacos上的服务
         *
         * UserDTO userDTO = restTemplate.getForObject(
         *                 "http://user-center/users/{id}",
         *                 UserDTO.class, userId
         *         );
         */

        Integer userId = share.getUserId();

        UserDTO userDTO = userCenterFeignClient.findById(userId);


        log.info(userDTO.toString());


        ShareDTO shareDTO = new ShareDTO();


        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());

        return shareDTO;

    }

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> forObject = restTemplate.getForEntity(
                "http://localhost:8080/users/{id}",
                String.class, 1
        );

        System.out.println("forObject = " + forObject.getBody());

        System.out.println("forObject.getStatusCode() = " + forObject.getStatusCode());


    }


}
