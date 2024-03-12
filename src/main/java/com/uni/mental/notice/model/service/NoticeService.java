package com.uni.mental.notice.model.service;

import com.uni.mental.notice.model.dao.NoticeDao;
import com.uni.mental.notice.model.dto.NoticeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {
    private final NoticeDao noticeDAO;
    public NoticeDTO selectOne(int no) {
        return noticeDAO.selectOne(no);
    }
    public NoticeService(NoticeDao noticeDAO) {
        this.noticeDAO = noticeDAO;
    }


    public List<NoticeDTO> getAllNotices() {
        return noticeDAO.getAllNotices();
    }

    public NoticeDTO getNoticeById(int noticeNo) {
        return noticeDAO.getNoticeById(noticeNo);
    }

    public void addNotice(NoticeDTO notice) {
        noticeDAO.addNotice(notice);
    }

    public void deleteNotice(int noticeNo) {
        noticeDAO.deleteNotice(noticeNo);
    }


    public int updateNotice(NoticeDTO noticeupdate) throws Exception {

        int result = noticeDAO.updateNotice(noticeupdate);

        if(result <=0){
            throw new Exception("게시물 수정 실패");
        }
        return result;
    }







}

