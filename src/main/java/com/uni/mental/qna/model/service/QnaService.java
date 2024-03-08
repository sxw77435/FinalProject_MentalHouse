package com.uni.mental.qna.model.service;

import com.uni.mental.qna.model.dao.QnaDao;
import com.uni.mental.qna.model.dto.QnaDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QnaService {
    private final QnaDao qnaDAO;


    public QnaService(QnaDao qnaDAO) {
        this.qnaDAO = qnaDAO;
    }


    public List<QnaDto> getAllQna() {
        return qnaDAO.getAllQna();
    }

    public QnaDto getQnaById(int qnaNo) {
        return qnaDAO.getQnaById(qnaNo);
    }

    public void addQna(QnaDto qna) {
        qnaDAO.addQna(qna);
    }

    public void deleteQna(int qnaNo) {
        qnaDAO.deleteQna(qnaNo);
    }

    public QnaDto selectOne(int no) {
        return qnaDAO.selectOne(no);
    }


    public List<QnaDto> findAllView() {

        return qnaDAO.findAllView();
    }

    public int updateQna(QnaDto qnaupdate) throws Exception {

        int result = qnaDAO.updateQna(qnaupdate);

        if(result <=0){
            throw new Exception("게시물 수정 실패");
        }
        return result;
    }


}


