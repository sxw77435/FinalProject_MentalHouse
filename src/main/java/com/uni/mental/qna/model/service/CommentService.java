package com.uni.mental.qna.model.service;

import com.uni.mental.qna.model.dao.CommentDao;
import com.uni.mental.qna.model.dto.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentDao commentDao;

    public CommentService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public int deleteReply(Integer pno) {

        return commentDao.deleteReply(pno);
    }

    public List<CommentDto> qnareplyList() {

        return commentDao.qnareplyList();

    }

    public int insertReply(CommentDto result) throws Exception {

        int comment = commentDao.insertReply(result);

        if(comment <=0){
            throw new Exception("게시물 수정 실패");
        }
        return comment;
    }

    public List<CommentDto> getList(CommentDto result) throws Exception {

        return commentDao.getList(result);
    }
}
