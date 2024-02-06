package com.uni.mental.member.model.service;

import com.uni.mental.member.model.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberService {

    private final MemberDao memberDao;

    @Autowired
    public MemberService(MemberDao memberDao){
        this.memberDao = memberDao;
    }


    public int idCheck(String id) {
        int result = memberDao.idCheck(id);

        if(result < 0 ) {
            throw new IllegalArgumentException("아이디체크에 실패하였습니다.");
        }
        return result;
    }

    public int nicknameCheck(String nick) {
        int result = memberDao.nicknameCheck(nick);

        if(result < 0 ) {
            throw new IllegalArgumentException("닉네임체크에 실패하였습니다.");
        }
        return result;
    }



}
