package com.uni.mental.ageComunity.model.service;

import com.uni.mental.ageComunity.model.dto.AgeComDTO;

import java.util.List;

public interface AgeComService {

    int registAgeCom(AgeComDTO ageCom) throws Exception;

    List<AgeComDTO> findAllView();

    AgeComDTO selectOne(int no);

//    int AgeCom(int no);

    int updateAgeCom(AgeComDTO ageCom) throws Exception;

    int deleteAgeCom(int no);
}
