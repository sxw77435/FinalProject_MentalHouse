package com.uni.mental.qna.model.dao;


import com.uni.mental.qna.model.dto.QnaDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaDao {

    List<QnaDto> findAllView();

    List<QnaDto> getAllQna();
    QnaDto getQnaById(int qnaNo);
    void addQna(QnaDto qna);

    void deleteQna(Integer qnaNo);

    QnaDto selectOne(int no);

    int updateQna(QnaDto qnaupdate);

}
