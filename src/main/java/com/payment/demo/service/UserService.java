package com.payment.demo.service;

import com.payment.demo.domain.User;

/**
 * @author Blue_Sky 7/23/21
 */
public interface UserService {
    User saveWeChatUser(String code);
}
