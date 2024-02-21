package com.uni.mental.agecomunity.model.dao;

import com.uni.mental.agecomunity.model.dto.AgeComDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgeComDAO {

    List<AgeComDTO> findAllView();
    AgeComDTO selectOne(int no);
    int registAgeCom(AgeComDTO ageCom);
    int updateAgeCom(AgeComDTO ageCom);

    int deleteAgeCom(int no);

}
