package com.uni.mental.member.model.service;

import com.uni.mental.member.model.dao.MemberDao;
import com.uni.mental.member.model.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberDao memberDao, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.passwordEncoder = passwordEncoder;
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


    public boolean authenticate(String username, String password) {
        MemberDto member = memberDao.findMemberById(username);

        // 멤버 못 찾거나 비번 틀렀을때 false로 나오기
        if (member == null || !passwordEncoder.matches(password, member.getPwd())) {
            return false;
        }

        return true;
    }

    public List<MemberDto> getAllUsers() {
        return memberDao.getAllUsers();
    }

    public String getEmailById(String memberId) {
        return memberDao.getEmailById(memberId);
    }

}
