package com.payment.demo.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.gson.Gson;
import com.payment.demo.utils.JsonData;
import com.payment.demo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Blue_Sky 7/23/21
 */
public class LoginInterceptor implements HandlerInterceptor {

    private static final Gson gson = new Gson();
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null){
            token = request.getParameter("token");
        }
        if (StringUtils.isNotBlank(token)){
            Claims claims = JwtUtils.checkJwtToken(token);
                if (claims != null){
                    Integer id = (Integer)claims.get("id");
                    String name = (String) claims.get("name");

                    request.setAttribute("user_id",id);
                    request.setAttribute("name",name);
                    return true;
                }
        }
        sendJsonMessage(response, JsonData.buildFailure("please login......."));
        return false;
    }

    public static void sendJsonMessage(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(gson.toJson(obj));
        printWriter.close();
        response.flushBuffer();
    }
}
