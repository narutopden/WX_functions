package com.payment.demo.controller;

import com.payment.demo.config.WechatConfig;
import com.payment.demo.domain.User;
import com.payment.demo.service.UserService;
import com.payment.demo.utils.JsonData;
import com.payment.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Blue_Sky 7/21/21
 */

@Controller
@RequestMapping("/api/v1/wechat")
public class WechatController {

    @Autowired
    WechatConfig wechatConfig;

    @Autowired
    UserService userService;

    @GetMapping("login")
    @ResponseBody
    public JsonData login(@RequestParam(value = "access_page") String accessPage) throws UnsupportedEncodingException {
         String redirectUrl = wechatConfig.getOpenRedirectUrl();

         String callBackUrl = URLEncoder.encode(redirectUrl,"GBK");

         String qrCodeUrl = String.format(wechatConfig.getOPEN_QRCODE_URL(),wechatConfig.getOpenAppId(),callBackUrl,accessPage);
        return JsonData.buildSuccess(qrCodeUrl,"success");
    }

    /**
     * call back location where wechat will redirect to
     * @param code
     * @param state
     * @param response
     */
    @GetMapping("callback")
    public void callback(@RequestParam(value = "code") String code,
                             String state, HttpServletResponse response) throws IOException {
        User user = userService.saveWeChatUser(code);
        if (user !=null){
            // produce jwt
            String token = JwtUtils.generateJwtToken(user);
                response.sendRedirect(state+"?token="+token+"&headImg="+user.getHeadImg()+"&userName="+URLEncoder.encode(user.getName(),"UTF-8"));


        }
    }
}
