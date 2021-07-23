package com.payment.demo.service.impl;

import com.payment.demo.domain.Video;
import com.payment.demo.mapper.VideoMapper;
import com.payment.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Blue_Sky 7/17/21
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Resource
    private VideoMapper videoMapper;

    @Override
    public List<Video> findAll(){
        return videoMapper.selectList(null);
    }

    @Override
    public Video findById(Integer id) {
        return videoMapper.selectById(id);
    }

    @Override
    public int updateById(Video video) {
       return videoMapper.updateById(video);
    }

    @Override
    public int deleteById(Integer id) {
       return videoMapper.deleteById(id);
    }

    @Override
    public int saveVideo(Video video) {
        return videoMapper.insert(video);
    }
}
