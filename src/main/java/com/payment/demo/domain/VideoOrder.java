package com.payment.demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoOrder implements Serializable {

  private Integer id;
  private String openid;
  private String outTradeNo;
  private Integer state;
  private java.sql.Date createTime;
  private java.sql.Date notifyTime;
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
