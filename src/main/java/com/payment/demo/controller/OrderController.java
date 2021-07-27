package com.payment.demo.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.payment.demo.dto.VideoOrderDto;
import com.payment.demo.service.VideoOrderService;
import com.payment.demo.service.VideoService;
import com.payment.demo.utils.IpUtils;
import com.payment.demo.utils.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Blue_Sky 7/23/21
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    VideoOrderService videoOrderService;


    @GetMapping("add")
    public void saveOrder(@RequestParam("video_id") int videoId,
                          HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        dataLogger.error("erreo");
        String ip = IpUtils.getIpAddr(request);
//        int userId = request.getAttribute("user_id");
        int userId =1;
        VideoOrderDto videoOrderDto =new VideoOrderDto();
        videoOrderDto.setVideoId(videoId);
        videoOrderDto.setUserId(userId);
        videoOrderDto.setIp(ip);
        String code_url = videoOrderService.saveOrder(videoOrderDto);
        if (code_url == null){
            throw new NullPointerException();
        }
        try {
            // 生成二维码配置
            Map<EncodeHintType,Object> hints = new HashMap<>();
            // setting up correction level [ idk ]
            hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);
            // 编码类型
            hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
            // QR code parameter
            BitMatrix bitMatrix = new MultiFormatWriter().encode(code_url, BarcodeFormat.QR_CODE,400 ,400 , hints);
            OutputStream outputStream = response.getOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix,"png",outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
