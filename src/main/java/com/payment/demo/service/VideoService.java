package com.payment.demo.service;

import com.payment.demo.domain.Video;

import java.util.List;

/**
 * @author Blue_Sky 7/17/21
 */
public interface VideoService {
     List<Video> findAll();
     Video findById(Integer id);
     int updateById(Video video);
     int deleteById(Integer id);
     int saveVideo(Video video);

}
