package com.payment.demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Video implements Serializable {

  private Integer id;
  private String title;
  private String summary;
  private String coverImg;
  private Integer viewNum;
  private Integer price;
  private java.util.Date createTime;
  private Integer online;
  private Double point;



}
