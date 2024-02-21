package com.uni.mental.mentalcomunity.model.service;

import com.uni.mental.mentalcomunity.model.dto.MenComDTO;

import java.util.List;

public interface MenComService {

    int registMenCom(MenComDTO menCom) throws Exception;

    List<MenComDTO> findAllView();

//    List<MenComDTO> findAllViewCount(RowBounds rowBounds);
    MenComDTO selectOne(int no);

    int updateMencom(MenComDTO menCom) throws Exception;

    int deleteMencom(int no);
}
