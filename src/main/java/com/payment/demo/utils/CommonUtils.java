package com.payment.demo.utils;

import java.security.MessageDigest;
import java.util.UUID;

/** common utilities
 * MD5 , UUID,
 * @author Blue_Sky 7/17/21
 */
public class CommonUtils {
    /**
     * produce UUID
     * @return
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

    public static String MD5(String data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] digest = md5.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : digest){
            sb.append(Integer.toHexString((item & 0xFF) | 0x100 ).substring(1,3));
        }
        return sb.toString().toUpperCase();
    }
}
