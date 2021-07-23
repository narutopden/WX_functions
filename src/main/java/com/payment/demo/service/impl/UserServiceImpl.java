package com.payment.demo.service.impl;

import com.payment.demo.config.WechatConfig;
import com.payment.demo.domain.User;
import com.payment.demo.mapper.UserMapper;
import com.payment.demo.service.UserService;
import com.payment.demo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * @author Blue_Sky 7/23/21
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WechatConfig wechatConfig;

    @Resource
    private UserMapper userMapper;

    @Override
    public User saveWeChatUser(String code) {

        String accessTokenUrl = String.format(wechatConfig.getOPEN_ACCESS_TOKEN_URL(), wechatConfig.getOpenAppId(), wechatConfig.getOpenAppSecret(), code);
        //fetching the access token
        Map<String, Object> baseMap = HttpUtils.get(accessTokenUrl);

        if (baseMap == null || baseMap.isEmpty()) {
            return null;
        }
        String access_token = (String) baseMap.get("access_token");
        String openid = (String) baseMap.get("openid");

        User userFromDb = userMapper.findByOpenId(openid);
        if (userFromDb != null) {
            return userFromDb;
        } else {


            //fetching the user data
            String userInfoUrl = String.format(wechatConfig.getOPEN_USERINFO_URL(), access_token, openid);
            Map<String, Object> userInfoMap = HttpUtils.get(userInfoUrl);
            if (userInfoMap == null || userInfoMap.isEmpty()) {
                return null;
            }
            String nickName = (String) userInfoMap.get("nickname");
            Double sexTmp = (Double) userInfoMap.get("sex");
            int sex = sexTmp.intValue();
            String city = (String) userInfoMap.get("city");
            String province = (String) userInfoMap.get("province");
            String country = (String) userInfoMap.get("country");
            String headimgurl = (String) userInfoMap.get("headimgurl");
            StringBuilder add = new StringBuilder(country).append("||").append(province).append("||").append(city);
            String address = add.toString();

                try {
                    nickName = new String(nickName.getBytes("ISO-8859-1"), "UTF-8");
                    address = new String(address.getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            User user = new User();
            user.setName(nickName);
            user.setHeadImg(headimgurl);
            user.setCity(address);
            user.setOpenid(openid);
            user.setSex(sex);
            user.setCreateTime(new Date());

            userMapper.saveUser(user);
            return user;
        }
    }
}
