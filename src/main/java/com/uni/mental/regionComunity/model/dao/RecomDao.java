package com.uni.mental.regionComunity.model.dao;

import com.uni.mental.regionComunity.model.dto.RecomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecomDao {


    int insertRecom(RecomDto recomDto);

    List<RecomDto> findAllRecom();

    RecomDto findRecomByCode(int recomno);

    int updateRecom(RecomDto recomDto);

    int updateRecomViews(int recomno, int recomviews);

    void deleteRecom(int recomno);

    List<RecomDto> findRecomByCateno(int cateno);
}
