package com.payment.demo.controller;

import com.payment.demo.config.WechatConfig;
import com.payment.demo.domain.User;
import com.payment.demo.domain.VideoOrder;
import com.payment.demo.service.UserService;
import com.payment.demo.service.VideoOrderService;
import com.payment.demo.utils.JsonData;
import com.payment.demo.utils.JwtUtils;
import com.payment.demo.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

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

    @Autowired
    VideoOrderService videoOrderService;

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
    @GetMapping("user/callback")
    public void callback(@RequestParam(value = "code") String code,
                             String state, HttpServletResponse response) throws IOException {
        User user = userService.saveWeChatUser(code);
        if (user !=null){
            // produce jwt
            String token = JwtUtils.generateJwtToken(user);
                response.sendRedirect(state+"?token="+token+"&headImg="+user.getHeadImg()+"&userName="+URLEncoder.encode(user.getName(),"UTF-8"));


        }
    }

    /**
     * wechat payment callback
     */
    @PostMapping("order/notify")
    public void orderCallback( HttpServletRequest request,HttpServletResponse response) throws Exception {
            InputStream inputStream = request.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null){
                stringBuffer.append(line);
            }
            in.close();
            inputStream.close();
            Map<String,String> callbackMap = WXPayUtil.xmlToMap(stringBuffer.toString());
            System.out.println(callbackMap);
            SortedMap<String, String> sortedMap = WXPayUtil.getSortedMap(callbackMap);

            // verify the authentication of key
            if (WXPayUtil.isSignCorrect(sortedMap,wechatConfig.getKey())){

                if (sortedMap.get("result_code").equals("SUCCESS")){

                    String outTradeNo = sortedMap.get("out_trade_no");
                    VideoOrder videoOrder = videoOrderService.findByOutTradeNo(outTradeNo);

                    if (videoOrder.getState() == 0) {
                            VideoOrder videoOrder1 = new VideoOrder();
                            videoOrder1.setOpenid(sortedMap.get("openid"));
                            videoOrder1.setOutTradeNo(outTradeNo);
                            videoOrder1.setNotifyTime(new Date());
                            videoOrder1.setState(1);
                            int flag = videoOrderService.updateVideoOrderByTradeNo(videoOrder1);

                            if (flag == 1){
                                System.out.println("===================== upadte line is there");
                                    response.setContentType("text/xml");
                                    response.getWriter().println(WXPayUtil.successMessage());
                            }
                    }
                }
            }
        response.setContentType("text/xml");
        response.getWriter().println(WXPayUtil.successMessage());

    }
}
