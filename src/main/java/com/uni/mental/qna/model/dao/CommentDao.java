package com.uni.mental.qna.model.dao;


import com.uni.mental.qna.model.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {
    List<CommentDto> qnareplyList();

    int insertReply(CommentDto result);

    List<CommentDto> getList(CommentDto result);

    int deleteReply(Integer pno);

}
