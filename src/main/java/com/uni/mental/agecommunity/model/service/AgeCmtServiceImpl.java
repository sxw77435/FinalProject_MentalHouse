package com.uni.mental.agecommunity.model.service;

import com.uni.mental.agecommunity.model.dao.AgeCmtDAO;
import com.uni.mental.agecommunity.model.dto.AgeCmtDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeCmtServiceImpl implements AgeCmtService {

    private final AgeCmtDAO ageCmtDAO;

    @Autowired
    public AgeCmtServiceImpl(AgeCmtDAO ageCmtDAO) {
        this.ageCmtDAO = ageCmtDAO;
    }

    @Override
    public List<AgeCmtDTO> getCommentsByAgeComNo(int ageComNo) {
        return ageCmtDAO.selectCommentsByAgeComNo(ageComNo);
    }

    @Override
    public void addComment(AgeCmtDTO comment) {
        ageCmtDAO.insertComment(comment);
    }

    @Override
    public void removeComment(int ageCmtNo) {
        ageCmtDAO.deleteComment(ageCmtNo);
    }

    @Override
    public void modifyComment(AgeCmtDTO comment) {
        ageCmtDAO.updateComment(comment);
    }

    @Override
    public void updateReplyCount(int ageComNo) {
        // 해당 게시글의 댓글 수를 업데이트하는 로직을 구현합니다.
        ageCmtDAO.updateReplyCount(ageComNo);
    }

}
