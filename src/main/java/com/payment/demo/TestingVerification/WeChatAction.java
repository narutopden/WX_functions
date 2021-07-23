package com.payment.demo.TestingVerification;

/** only for wechat developer testing account url verification
 * @author Blue_Sky 7/21/21
 */
import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.payment.demo.TestingVerification.CheckoutUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
public class WeChatAction {
    /**
     * 微信消息接收和token验证
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/weChatToken.do")
    public void weChat(Model model, HttpServletRequest request,HttpServletResponse response) throws IOException {
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        System.out.println(isGet);
        PrintWriter print;
        if (isGet) {
            // 微信加密签名
            String signature = request.getParameter("signature");
            System.out.println("signature: "+signature);
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            System.out.println("timestamp: "+timestamp);
            // 随机数
            String nonce = request.getParameter("nonce");
            System.out.println("nonce: "+nonce);
            // 随机字符串
            String echostr = request.getParameter("echostr");
            System.out.println("echostr: "+echostr);
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (signature != null && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
                try {
                    print = response.getWriter();
                    print.write(echostr);
                    print.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(response);
            }

        }

    }



}