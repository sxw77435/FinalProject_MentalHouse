package com.uni.mental.mypage.model.dao;

import com.uni.mental.mypage.model.dto.MypageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageDao {

    List<MypageDto> getMypageById(String id);

    MypageDto mypageDetail(int no);


    void updateMypage(MypageDto mypageupdate);
    void deleteMypage(Integer  mypageNo);
    // 비밀번호 업데이트 메서드 추가



}
