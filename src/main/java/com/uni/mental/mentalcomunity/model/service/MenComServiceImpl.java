package com.uni.mental.mentalcomunity.model.service;

import com.uni.mental.mentalcomunity.model.dao.MenComDAO;
import com.uni.mental.mentalcomunity.model.dto.MenComDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenComServiceImpl implements MenComService{

    private final MenComDAO menComDAO;


    public MenComServiceImpl(MenComDAO menComDAO) {
        this.menComDAO = menComDAO;
    }

    public int registMenCom(MenComDTO menCom) throws Exception {
        int result = menComDAO.registMenCom(menCom);

        if(result <=0){
            throw new Exception("게시물 등록 실패");
        }
        return result;
    }

    @Override
    public List<MenComDTO> findAllView() {

        return menComDAO.findAllView();
    }

//    @Override
//    public List<MenComDTO> findAllViewCount(RowBounds rowBounds) {
//        return menComDAO.findAllViewCount(rowBounds);
//    }

    @Override
    public MenComDTO selectOne(int no) {
        return menComDAO.selectOne(no);
    }

    @Override
    public int updateMencom(MenComDTO menCom) throws Exception {
        int result = menComDAO.registMenCom(menCom);

        if(result <=0){
            throw new Exception("게시물 수정 실패");
        }
        return result;
    }

    @Override
    public int deleteMencom(int no) {

        return menComDAO.deleteMencom(no);
    }


}
