package com.uni.mental.main.model.service;

import com.uni.mental.main.model.dao.MainDAO;
import com.uni.mental.main.model.dto.MainDTO;
import com.uni.mental.notice.model.dto.NoticeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {
    private final MainDAO mainDAO;

    public MainService(MainDAO mainDAO) {
        this.mainDAO = mainDAO;
    }

    public List<MainDTO> getAllPostsOrderByDateDesc() {
        return mainDAO.selectAllJoinedTablesOrderByDateDesc();
    }


    public List<NoticeDTO> getNotices() {  // 공지사항 목록을 가져오는 메소드 추가
        return mainDAO.selectNotices();
    }
}
