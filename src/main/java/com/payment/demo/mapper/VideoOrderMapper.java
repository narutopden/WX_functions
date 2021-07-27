package com.payment.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.payment.demo.domain.Video;
import com.payment.demo.domain.VideoOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * order mapper layer
 * @author Blue_Sky 7/24/21
 */
public interface VideoOrderMapper extends BaseMapper<VideoOrder> {

@Select("select * from video_order where out_trade_no = #{outTradeNo} and del=0 and state=0")
 VideoOrder findVideoTime(String outTradeNo);


}
