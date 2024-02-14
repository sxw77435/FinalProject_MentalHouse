package com.uni.mental.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CheckupMapper {
    @Select("SELECT mental_name FROM tbl_mental_list")
    List<String> selectAllMentalNames();

    @Select("SELECT cu_question FROM tbl_checkup WHERE mental_name = #{mentalName}")
    List<String> selectQuestionsByMentalName(String mentalName);

    @Select("SELECT mental_name FROM tbl_mental_list WHERE mental_no = #{idx}")
    String selectMentalNameByIdx(int idx);

}
