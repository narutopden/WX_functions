package com.payment.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class VideoOrder implements Serializable {

  @TableId(type = IdType.AUTO)
  private Integer id;
  private String openid;
  private String outTradeNo;
  private Integer state;
  private Date createTime;
  private Date notifyTime;
  private Integer totalFee;
  private String nickname;
  private String headImg;
  private Integer videoId;
  private String videoTitle;
  private String videoImg;
  private Integer userId;
  private String ip;
  private Integer del;



}
