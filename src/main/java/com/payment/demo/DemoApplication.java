package com.payment.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.payment.demo.mapper")
public class DemoApplication {

          public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        System.out.println();
    }

}
