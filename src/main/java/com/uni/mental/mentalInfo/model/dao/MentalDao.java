package com.uni.mental.mentalInfo.model.dao;

import com.uni.mental.mentalInfo.model.dto.AttachDto;
import com.uni.mental.mentalInfo.model.dto.MentalDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MentalDao {

    int insertMental(MentalDto mentalDto);

    List<MentalDto> findAllMental();

    int insertAttach(AttachDto attachDto);

    String getAttachOrinameByAttachNo(int attachNo);
}
