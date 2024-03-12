package com.uni.mental.mypage.model.service;

import com.uni.mental.mypage.model.dao.MypageDao;
import com.uni.mental.mypage.model.dto.MypageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MypageServiceImpl implements MypageService {

    private final MypageDao mypageDao;

    @Autowired
    public MypageServiceImpl(MypageDao mypageDao) {
        this.mypageDao = mypageDao;
    }

    @Override
    public List<MypageDto> getMypageById(String id) {
        return mypageDao.getMypageById(id);
    }

    public MypageDto mypageDetail(int no) {
        return mypageDao.mypageDetail(no);
    }

    @Override
    public MypageDto updateMypage(MypageDto mypageupdate) {
        mypageDao.updateMypage(mypageupdate);

        return mypageupdate;
    }

    @Override
    public void deleteMypage(int mypageNo) {
        mypageDao.deleteMypage(mypageNo);
    }

}
