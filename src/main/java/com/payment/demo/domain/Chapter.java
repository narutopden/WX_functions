package com.payment.demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Chapter implements Serializable {

  private Integer id;
  private Integer videoId;
  private String title;
  private Integer ordered;
  private java.sql.Date createTime;


}
