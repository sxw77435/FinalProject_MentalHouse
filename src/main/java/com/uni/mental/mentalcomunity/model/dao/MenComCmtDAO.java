package com.uni.mental.mentalcomunity.model.dao;

import com.uni.mental.mentalcomunity.model.dto.MenComCmtDTO;
import com.uni.mental.mentalcomunity.model.dto.MenComDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenComCmtDAO {


    List<MenComDTO> mencmtList();

    int insertComment(MenComCmtDTO result);

    List<MenComCmtDTO> getList(MenComCmtDTO result);

    int deleteComment(int no);
}
