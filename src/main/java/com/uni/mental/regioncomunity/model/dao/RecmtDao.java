package com.uni.mental.regioncomunity.model.dao;

import com.uni.mental.regioncomunity.model.dto.RecomCmtDto;
import com.uni.mental.regioncomunity.model.dto.RecomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecmtDao {
    List<RecomDto> recmtList();

    int insertRecmt(RecomCmtDto result);

    List<RecomCmtDto> getRecmtList(RecomCmtDto result);

    int deleteRecmt(int recmtno);
}
