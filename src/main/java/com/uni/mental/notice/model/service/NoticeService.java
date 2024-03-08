package com.uni.mental.notice.model.service;

import com.uni.mental.notice.model.dao.NoticeDao;
import com.uni.mental.notice.model.dto.NoticeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    private final NoticeDao noticeDAO;
    public NoticeDto selectOne(int no) {
        return noticeDAO.selectOne(no);
    }
    public NoticeService(NoticeDao noticeDAO) {
        this.noticeDAO = noticeDAO;
    }


    public List<NoticeDto> getAllNotices() {
        return noticeDAO.getAllNotices();
    }

    public NoticeDto getNoticeById(int noticeNo) {
        return noticeDAO.getNoticeById(noticeNo);
    }

    public void addNotice(NoticeDto notice) {
        noticeDAO.addNotice(notice);
    }

    public void deleteNotice(int noticeNo) {
        noticeDAO.deleteNotice(noticeNo);
    }


    public int updateNotice(NoticeDto noticeupdate) throws Exception {

        int result = noticeDAO.updateNotice(noticeupdate);

        if(result <=0){
            throw new Exception("게시물 수정 실패");
        }
        return result;
    }







}

