package com.itmuch.contentcenter;

import com.itmuch.contentcenter.dao.content.ShareMapper;

import com.itmuch.contentcenter.domain.entity.content.Share;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
public class testController {
    @Resource
    private ShareMapper shareMapper;

    @GetMapping("/test")
    public List<Share> test(){
        Share share = new Share();
        share.setCreateTime(new Date());
        share.setUpdateTime(new Date());
        share.setTitle("xxx");
        share.setCover("中国");
        share.setAuthor("songhao");
        share.setBuyCount(1);

        shareMapper.insertSelective(share);

        List<Share> shares = shareMapper.selectAll();
        return shares;


    }

}
