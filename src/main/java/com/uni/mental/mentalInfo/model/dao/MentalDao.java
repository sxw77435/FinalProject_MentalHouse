package com.uni.mental.mentalInfo.model.dao;

import com.uni.mental.mentalInfo.model.dto.AttachDto;
import com.uni.mental.mentalInfo.model.dto.MentalDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MentalDao {

    int insertMental(MentalDto mentalDto);

    int insertAttach(AttachDto attachDto);

    List<MentalDto> findAllMental();


    MentalDto findMentalByCode(int mentalinfono);

    int updateAttach(AttachDto attachDto);

    int updateMental(MentalDto mentalDto);

    void deleteMental(int mentalinfono);

    void deleteAttach(int mentalinfono);

    List<MentalDto> searchMentalByKeyword(String keyword);
}

