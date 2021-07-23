package com.payment.demo.domain;

import lombok.Data;
import java.sql.Date;
import java.io.Serializable;

@Data
public class Episode implements Serializable {

  private Integer id;
  private String title;
  private Integer num;
  private String duration;
  private String coverImg;
  private Integer videoId;
  private String summary;
  private Date createTime;
  private Integer chapterId;



}
