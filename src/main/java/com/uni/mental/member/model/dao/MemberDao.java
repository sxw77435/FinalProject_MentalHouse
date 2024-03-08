package com.uni.mental.member.model.dao;

import com.uni.mental.member.model.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {
    MemberDto findMemberById(String memberId);

    void enrollMember(MemberDto memberDto);

    int idCheck(String id);

    int nicknameCheck(String nick);


    List<MemberDto> getAllUsers();

    String getEmailById(String memberId);

    MemberDto findByEmail(String email);
}
