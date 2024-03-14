package com.uni.mental.notice.model.dao;

import com.uni.mental.notice.model.dto.NoticeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface NoticeDao {
    List<NoticeDto> getAllNotices();
    NoticeDto getNoticeById(int noticeNo);
    void addNotice(NoticeDto notice);
    int updateNotice(NoticeDto noticeupdate);
    void deleteNotice(Integer  noticeNo);
    NoticeDto selectOne(int no);

}
