package com.uni.mental.video.model.dao;

import com.uni.mental.video.model.dto.VideoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoDAO {
    List<VideoDTO> findAllView() ;

    int registVideo(VideoDTO videoDTO);

    VideoDTO selectOne(int no);

    int deleteVideo(int no);
}
