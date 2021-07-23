package com.payment.demo.controller.admin;

import com.payment.demo.domain.Video;
import com.payment.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Blue_Sky 7/19/21
 */
@RestController
@RequestMapping("admin/api/v1/video")
public class VideoAdminCOntroller {

    @Autowired
    VideoService videoService;

    /**
     * deleting video through their ID
     * @param id
     * @return
     */
    @DeleteMapping("del_by_id")
    public Object deleteById(@RequestParam(value = "userId") Integer id){
        return videoService.deleteById(id);
    }

    /**
     * updating the video
     * @param video
     * @return
     */
    @PutMapping("update_by_id")
    public Object updateById(@RequestBody Video video){
        return videoService.updateById(video);
    }

    /**
     * savinf a new entiry
     * @param video
     * @return
     */
    @PostMapping("save")
    public Object saveVideo(@RequestBody Video video){
        return videoService.saveVideo(video);
    }
}
