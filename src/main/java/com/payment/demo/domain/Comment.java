package com.payment.demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Comment implements Serializable {

  private Integer id;
  private String content;
  private Integer userId;
  private String headImg;
  private String name;
  private Double point;
  private Integer up;
  private java.sql.Date createTime;
  private Integer orderId;
  private Integer videoId;



}
