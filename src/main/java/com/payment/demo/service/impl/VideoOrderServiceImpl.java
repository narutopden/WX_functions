package com.payment.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.payment.demo.config.WechatConfig;
import com.payment.demo.domain.User;
import com.payment.demo.domain.Video;
import com.payment.demo.domain.VideoOrder;
import com.payment.demo.dto.VideoOrderDto;
import com.payment.demo.mapper.UserMapper;
import com.payment.demo.mapper.VideoMapper;
import com.payment.demo.mapper.VideoOrderMapper;
import com.payment.demo.service.VideoOrderService;
import com.payment.demo.utils.CommonUtils;
import com.payment.demo.utils.HttpUtils;
import com.payment.demo.utils.WXPayUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Blue_Sky 7/24/21
 */
@Service
//@RequiredArgsConstructor
public class VideoOrderServiceImpl implements VideoOrderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    VideoOrderMapper videoOrderMapper;

    @Autowired
    VideoMapper videoMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    WechatConfig wechatConfig;

    /**
     * save order logic
     * @param videoOrderDto
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String saveOrder(VideoOrderDto videoOrderDto) throws Exception {

        dataLogger.info("module=video_order`api=save_order`user_id={}`video_id={}",videoOrderDto.getUserId(),videoOrderDto.getVideoId());

        Video isVideoPresent = videoMapper.selectById(videoOrderDto.getVideoId());
        User isUserPresent = userMapper.selectById(videoOrderDto.getUserId());
        // wrapping the video order
        VideoOrder videoOrder = new VideoOrder();

        videoOrder.setOpenid(isUserPresent.getOpenid());
        videoOrder.setOutTradeNo(CommonUtils.generateUUID());
        videoOrder.setState(0);
        videoOrder.setCreateTime(new Date());
//        videoOrder.setNotifyTime(new Date());
        videoOrder.setTotalFee(isVideoPresent.getPrice());
        videoOrder.setNickname(isUserPresent.getName());
        videoOrder.setHeadImg(isUserPresent.getHeadImg());
        videoOrder.setVideoId(videoOrderDto.getVideoId());
        videoOrder.setVideoTitle(isVideoPresent.getTitle());
        videoOrder.setVideoImg(isVideoPresent.getCoverImg());
        videoOrder.setUserId(videoOrderDto.getUserId());
        videoOrder.setIp(videoOrderDto.getIp());
        videoOrder.setDel(0);

        //generate order
        videoOrderMapper.insert(videoOrder);

        //获取 code_url for QR code
        String code_url = unifiedOrder(videoOrder);
        // generating QR code
        return code_url;
    }
    //order process
        public String unifiedOrder(VideoOrder videoOrder) throws Exception {
        SortedMap<String,String> sortedMap = new TreeMap<>();
        sortedMap.put("appid", wechatConfig.getAppId());
        sortedMap.put("mch_id", wechatConfig.getMchId());
        sortedMap.put("nonce_str",CommonUtils.generateUUID());
        sortedMap.put("body",videoOrder.getVideoTitle());
        sortedMap.put("out_trade_no",videoOrder.getOutTradeNo());
        sortedMap.put("total_fee",videoOrder.getTotalFee().toString());
        sortedMap.put("spbill_create_ip",videoOrder.getIp());
        sortedMap.put("notify_url",wechatConfig.getPayCallbackUrl());
        sortedMap.put("trade_type","NATIVE");

        // generating signature
        String signature = WXPayUtil.createSignature(sortedMap, wechatConfig.getKey());
        sortedMap.put("sign",signature);

        // covert Map to XML
            String xml = WXPayUtil.mapToXml(sortedMap);
//            System.out.println(xml);

        // Unified order | 获取 codeurl for QR code
            String orderQR = HttpUtils.post(wechatConfig.getUNIFIED_ORDER_URL(), xml, 4000);
//            System.out.println(new String(orderQR.getBytes("ISO-8859-1"), "UTF-8"));
            if (orderQR == null){ return null; }

            Map<String, String> map = WXPayUtil.xmlToMap(orderQR);
            
            if (map != null) { return map.get("code_url"); }
            return null;
        }

    @Override
    public VideoOrder findById(int id) {
        videoOrderMapper.selectById(id);
        return null;
    }

    @Override
    public VideoOrder findByOutTradeNo(String outTradeNo) {

        QueryWrapper<VideoOrder> queryWrapper = new QueryWrapper<VideoOrder>().eq("out_trade_no", outTradeNo);
        VideoOrder videoOrder =videoOrderMapper.selectOne(queryWrapper);
        System.out.println(queryWrapper);
//        VideoOrder videoOrder = videoOrderMapper.findVideoTime(outTradeNo);
        return videoOrder;
    }

    /**
     * delete by changing del to 0 and not by actual delete
     * @param id
     * @param userId
     * @return
     */
    @Override
    public int deleteVideoOrder(int id, int userId) {
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setDel(1);
        QueryWrapper<VideoOrder> queryWrapper = new QueryWrapper<VideoOrder>().eq("id", id).eq("user_id", userId);
        int update = videoOrderMapper.update(videoOrder, queryWrapper);
        return update;
    }

    /**
     * find all of users order
     * @param userId
     * @return
     */
    @Override
    public List<VideoOrder> findMyOrderList(int userId) {
        QueryWrapper<VideoOrder> queryWrapper = new QueryWrapper<VideoOrder>().eq("user_id", userId);
        videoOrderMapper.selectList(queryWrapper);
        return null;
    }

    /**
     * updates order table with results from wechat merchant
     * @param videoOrder
     * @return
     */
    @Override
    public int updateVideoOrderByTradeNo(VideoOrder videoOrder) {
        VideoOrder tmp = new VideoOrder();
        tmp.setState(videoOrder.getState());
        tmp.setNotifyTime(videoOrder.getNotifyTime());
        tmp.setOpenid(videoOrder.getOpenid());
        QueryWrapper<VideoOrder> queryWrapper = new QueryWrapper<VideoOrder>().eq("out_trade_no", videoOrder.getOutTradeNo())
                                                                              .eq("state", 0)
                                                                              .eq("del", 0);
         return videoOrderMapper.update(tmp,queryWrapper);
    }


}
