package com.payment.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Blue_Sky 7/18/21
 */
@Data
@Configuration
public class WechatConfig {

    @Value("${wxpay.appid}")
    private String appId;

    @Value("${wxpay.appsecret}")
    private String appSecret;

    @Value("${wxopen.appid}")
    private String openAppId;

    @Value("${wxopen.appsecret}")
    private String openAppSecret;

    @Value("${wxopen.redirect_url}")
    private String openRedirectUrl;

    /**
     *  link for QR code fetching
     */
   private final String OPEN_QRCODE_URL= "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";


    /**
     * link for wechat web interface login not QR code
     */
//    private final String OPEN_QRCODE_URL= "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirec";

    /**
     * link to get access_token from wechat
     */
    private final String OPEN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    /**
     * link for accessing user info
     * http请求方式: GET
     */
    private final String OPEN_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";


    /**
     * merchant id
     */
    @Value("${wxpay.mer_id}")
    private String mchId;


    @Value("${wxpay.key}")
    private String key;

    @Value("${wxpay.callback}")
    private String payCallbackUrl;

    private final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";



}
