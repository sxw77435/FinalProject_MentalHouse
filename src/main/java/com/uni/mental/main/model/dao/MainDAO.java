package com.uni.mental.main.model.dao;

import com.uni.mental.main.model.dto.MainDTO;
import com.uni.mental.notice.model.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MainDAO {
    List<MainDTO> selectAllJoinedTablesOrderByDateDesc();
    List<NoticeDTO> selectNotices();  // 공지사항 목록을 가져오는 메소드 추가

}
