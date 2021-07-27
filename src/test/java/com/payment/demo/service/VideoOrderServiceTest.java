package com.payment.demo.service;

import com.payment.demo.mapper.VideoOrderMapper;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Blue_Sky 7/24/21
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
class VideoOrderServiceTest {

    @Autowired
    VideoOrderMapper videoOrderMapper;
    @Test
    void findById() {
        System.out.println(videoOrderMapper.selectById(3));
    }

    @Test
    void findMyOrderList() {
        System.out.println(videoOrderMapper.selectById(2));
    }


}