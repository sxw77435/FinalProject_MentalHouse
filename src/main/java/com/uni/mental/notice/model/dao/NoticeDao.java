package com.uni.mental.notice.model.dao;

import com.uni.mental.notice.model.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface NoticeDao {
    List<NoticeDTO> getAllNotices();
    NoticeDTO getNoticeById(int noticeNo);
    void addNotice(NoticeDTO notice);
    int updateNotice(NoticeDTO noticeupdate);
    void deleteNotice(Integer  noticeNo);
    NoticeDTO selectOne(int no);

}
