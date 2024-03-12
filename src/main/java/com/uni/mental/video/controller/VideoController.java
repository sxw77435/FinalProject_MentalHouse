package com.uni.mental.video.controller;

import com.uni.mental.video.model.dto.VideoDTO;
import com.uni.mental.video.model.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("video")
public class VideoController {

    public final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("video_list")
    public void video(Model model)  {
        List<VideoDTO> videoList = videoService.findAllView();
        System.out.println(videoList);
        model.addAttribute("videoList",videoList);

    }

    @GetMapping("video_enroll")
    public void videoEnrollForm(){}

    @PostMapping("regist")
    public String registVideo(VideoDTO videoDTO){

        videoService.registVideo(videoDTO);

        return "redirect:/video/video_list";
    }
    @GetMapping("video_detail")
    public ModelAndView videoDetailView(@RequestParam("no") String no, ModelAndView mv){

        VideoDTO video = videoService.selectOne(Integer.parseInt(no));
        mv.addObject("video", video);
        mv.setViewName("video/video_detail");
        System.out.println(video);


        return mv;
    }

    @PostMapping("delete")
    public String videoDelete(@RequestParam("no") String no){

        videoService.deleteVideo(Integer.parseInt(no));

        return "redirect:/video/video_list";
    }
}
