package com.payment.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.payment.demo.config.PageHelperConfig;
import com.payment.demo.domain.Video;
import com.payment.demo.service.VideoService;
import com.payment.demo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Blue_Sky 7/17/21
 */
@RestController
@RequestMapping("api/v1/video")
public class VideoController {
    @Autowired
    private VideoService videoService;



    /**
     * get page from data
     * @param page the number of page we want
     * @param size number of data in a page aka page size
     * @return
     */
    @GetMapping("page")
    public Object getPage(@RequestParam(value = "page", defaultValue = "1") int page,
                          @RequestParam(value = "size", defaultValue = "7") int size){
        PageHelper.startPage(page,size);
        List<Video> list = videoService.findAll();
        PageInfo<Video> pageInfo = new PageInfo<>(list);

        return pageInfo;
    }

    /**
     * looking for specific video thought ID
     * @param id
     * @return
     */
    @GetMapping("find_by_id")
    public JsonData findById(@RequestParam(value = "userId") Integer id){
        return JsonData.buildSuccess(videoService.findById(id),"success");
    }


}
