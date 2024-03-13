package com.uni.mental.regioncomunity.model.dao;

import com.uni.mental.regioncomunity.model.dto.RecomCmtDto;
import com.uni.mental.regioncomunity.model.dto.RecomDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface RecmtDao {
    List<RecomDto> recmtList();

    int insertRecmt(RecomCmtDto result);

    List<RecomCmtDto> getRecmtList(RecomCmtDto result);

    int deleteRecmt(int recmtno);

    void updateReplyCnt( @RequestParam("recomno") int recomno,
                         @RequestParam("amount") int amount);
}
