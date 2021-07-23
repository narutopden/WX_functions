package com.payment.demo.controller;

import com.payment.demo.config.WechatConfig;
import com.payment.demo.domain.Video;
import com.payment.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Blue_Sky 7/17/21
 */
@RestController
public class test {

    @Autowired
    private VideoService videoService;
    @Autowired
    WechatConfig wechatConfig;
    @GetMapping("test")
    public List testConfig(){
        return videoService.findAll();
    }

    @GetMapping("find")
    public Video fingById(@RequestParam(value = "userId") Integer id){
        return videoService.findById(id);
    }
}
