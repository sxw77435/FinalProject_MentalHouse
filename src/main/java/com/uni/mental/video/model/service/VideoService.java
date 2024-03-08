package com.uni.mental.video.model.service;

import com.uni.mental.video.model.dao.VideoDAO;
import com.uni.mental.video.model.dto.VideoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    public final VideoDAO videoDAO;

    public VideoService(VideoDAO videoDAO) {
        this.videoDAO = videoDAO;
    }

    public List<VideoDTO> findAllView() {

        return videoDAO.findAllView();
    }

    public int registVideo(VideoDTO videoDTO) {

        return videoDAO.registVideo(videoDTO);
    }

    public VideoDTO selectOne(int no) {

        return videoDAO.selectOne(no);
    }

    public int deleteVideo(int no) {

        return videoDAO.deleteVideo(no);
    }
}
