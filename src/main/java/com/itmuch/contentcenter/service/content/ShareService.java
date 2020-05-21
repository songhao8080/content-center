package com.itmuch.contentcenter.service.content;

import com.alibaba.fastjson.JSONObject;
import com.itmuch.contentcenter.dao.content.ShareMapper;
import com.itmuch.contentcenter.domain.dto.content.ShareDTO;
import com.itmuch.contentcenter.domain.dto.user.UserDTO;
import com.itmuch.contentcenter.domain.entity.content.Share;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
@Slf4j
public class ShareService {
    @Resource
    private ShareMapper shareMapper;

    @Autowired
    private RestTemplate restTemplate;

    public ShareDTO findById(Integer id){


        Share share = shareMapper.selectByPrimaryKey(id);
        log.info(share.toString());

        Integer userId = share.getUserId();
        UserDTO userDTO = restTemplate.getForObject(
                "http://localhost:8080/users/{id}",
                UserDTO.class,userId
        );
        log.info(userDTO.toString());


        ShareDTO shareDTO = new ShareDTO();


        BeanUtils.copyProperties(share,shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());

        return shareDTO;

    }

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> forObject = restTemplate.getForEntity(
                "http://localhost:8080/users/{id}",
                String.class,1
        );

        System.out.println("forObject = " + forObject.getBody());

        System.out.println("forObject.getStatusCode() = " + forObject.getStatusCode());


    }





}
