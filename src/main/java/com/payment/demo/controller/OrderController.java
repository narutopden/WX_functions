package com.payment.demo.controller;

import com.payment.demo.utils.JsonData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Blue_Sky 7/23/21
 */
@RestController
@RequestMapping("auth/api/v1/order")
public class OrderController {

    @GetMapping("add")
    public JsonData order(){
        return JsonData.buildSuccess("order successful");
    }
}
