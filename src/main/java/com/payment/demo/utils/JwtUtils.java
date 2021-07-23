package com.payment.demo.utils;

import com.payment.demo.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author Blue_Sky 7/20/21
 */
public class JwtUtils {
    public final static String SUBJECT = "PayMentCode";
    public final static String APPSECRET = "toooppppdd";
    public final static long EXPIRE = 1000*60*60*24*7;

    public static String generateJwtToken(User user){
        if(user == null || user.getId() == null || user.getName() == null || user.getHeadImg() == null){
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id",user.getId())
                .claim("name",user.getName())
                .claim("img",user.getHeadImg())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
                .signWith(SignatureAlgorithm.HS256,APPSECRET).compact();
        return token;
    }

    public static Claims checkJwtToken(String token){
        try {
            final Claims body = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
            return body;
        }catch (Exception e){
            return null;
        }

    }
}
