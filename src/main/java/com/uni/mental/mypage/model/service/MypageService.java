package com.uni.mental.mypage.model.service;

import com.uni.mental.mypage.model.dto.MypageDto;

import java.util.List;

public interface MypageService {

    List<MypageDto> getMypageById(String id);

    MypageDto mypageDetail(int id);


    MypageDto updateMypage(MypageDto mypageupdate);

    void deleteMypage(int mypageNo);
}