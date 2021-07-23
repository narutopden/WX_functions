package com.payment.demo;

import com.payment.demo.domain.User;
import com.payment.demo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
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
}
