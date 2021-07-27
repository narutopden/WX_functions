package com.payment.demo.service;

import com.payment.demo.domain.VideoOrder;
import com.payment.demo.dto.VideoOrderDto;

import java.util.List;

/**
 * @author Blue_Sky 7/24/21
 */
public interface VideoOrderService {

    /**
     * save order interface
     * @param videoOrderDto
     * @return
     */
    String saveOrder(VideoOrderDto videoOrderDto) throws Exception;

    VideoOrder findById(int id);

    VideoOrder findByOutTradeNo(String outTradeNo);

    int deleteVideoOrder(int id,int userId);

    List<VideoOrder> findMyOrderList(int userId);

    int updateVideoOrderByTradeNo(VideoOrder videoOrder);
}
