package com.uni.mental.regioncomunity.model.service;


import com.uni.mental.regioncomunity.model.dao.RecmtDao;
import com.uni.mental.regioncomunity.model.dto.RecomCmtDto;
import com.uni.mental.regioncomunity.model.dto.RecomDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class RecmtService {

    private final RecmtDao recmtDao;

    public RecmtService(RecmtDao recmtDao) {
        this.recmtDao = recmtDao;
    }
    public List<RecomDto> recmtList() {
        return recmtDao.recmtList();

    }

    public int insertRecmt(RecomCmtDto result) throws Exception {

        recmtDao.updateReplyCnt(result.getRecomno(),1);
        int comment = recmtDao.insertRecmt(result);

        if(comment <=0){
            throw new Exception("게시물 수정 실패");
        }
        return comment;
    }

    public List<RecomCmtDto> getRecmtList(RecomCmtDto result) {
        return recmtDao.getRecmtList(result);

    }

    public int deleteRecmt(int recmtno) {
        return recmtDao.deleteRecmt(recmtno);

    }
}
