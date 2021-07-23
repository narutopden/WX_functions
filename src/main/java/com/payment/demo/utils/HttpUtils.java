package com.payment.demo.utils;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Blue_Sky 7/21/21
 */
public class HttpUtils {

    private static final Gson jsonGson= new Gson();

    public static Map<String, Object> get(String url){

        Map<String, Object> map = new HashMap<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000) // Connection timeout
                .setConnectionRequestTimeout(5000) // Request timeout
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)
                .build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200){

               String jsonResult = EntityUtils.toString(httpResponse.getEntity());
               map = jsonGson.fromJson(jsonResult,map.getClass());
            }
        }catch (Exception e){
                e.printStackTrace();
        }finally {
                try {
                    httpClient.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
        return map;
    }

    public static String post(String url, String data, int timeout){
        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout) // Connection timeout
                .setConnectionRequestTimeout(timeout) // Request timeout
                .setSocketTimeout(timeout)
                .setRedirectsEnabled(true)
                .build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content_type","text/html; charset_=UTF-8");

        if (data != null && data instanceof String){// only string is allowed
            StringEntity stringEntity = new StringEntity(data,"UTF-8");
                httpPost.setEntity(stringEntity);
        }
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpResponse.getStatusLine().getStatusCode() == 200){

                String result = EntityUtils.toString(httpEntity);
                return result;
            }
        }catch (Exception e){
                e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
