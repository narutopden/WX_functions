package com.payment.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.payment.demo.domain.User;
import com.payment.demo.domain.VideoOrder;
import com.payment.demo.mapper.VideoOrderMapper;
import com.payment.demo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    VideoOrderMapper videoOrderMapper;

//    @Test
    void contextLoads() {
    }

//    @Test
    void testJwt(){
        User user = new User();
//        user.setId(123);
        user.setName("tenzin");
        user.setHeadImg("afsdfas.jpg");

        String generateJwtToken = JwtUtils.generateJwtToken(user);
        System.out.println(generateJwtToken);

        Claims claims = JwtUtils.checkJwtToken(generateJwtToken);
        System.out.println(claims.toString());
    }
    @Test
    void  findByOutTradeNo() {
        String outTradeNo = "3432";
        QueryWrapper<VideoOrder> queryWrapper = new QueryWrapper<VideoOrder>().eq("out_trade_no", outTradeNo).eq("del", 0);
        System.out.println(videoOrderMapper.selectList(queryWrapper));
    }

    @Test
    void delete(){
        int id =1;
        int userId = 1;
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setDel(1);
        QueryWrapper<VideoOrder> queryWrapper = new QueryWrapper<VideoOrder>().eq("id", id).eq("user_id", userId);
        int update = videoOrderMapper.update(videoOrder, queryWrapper);
        System.out.println(update);
    }

    @Test
    void insert(){
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setDel(0);
        videoOrder.setOutTradeNo("232323");
        videoOrder.setCreateTime(new Date());
        videoOrder.setHeadImg("234235");
        videoOrder.setIp("fsdfsd");
        videoOrder.setNickname("kkkkk");
        videoOrder.setNotifyTime(new Date());
        int insert = videoOrderMapper.insert(videoOrder);
        System.out.println(insert);
    }
}
