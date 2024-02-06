package com.uni.mental.member.model.dto;

public class MemberRoleDTO {
    private int memberNo;					// 회원번호
    private int authorityNo;				// 권한코드

    private AuthorityMemberDTO authority;	// 회원보유권한

    public MemberRoleDTO() {
    }

    public MemberRoleDTO(int memberNo, int authorityNo, AuthorityMemberDTO authority) {
        this.memberNo = memberNo;
        this.authorityNo = authorityNo;
        this.authority = authority;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public int getAuthorityNo() {
        return authorityNo;
    }

    public void setAuthorityNo(int authorityCode) {
        this.authorityNo = authorityNo;
    }

    public AuthorityMemberDTO getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityMemberDTO authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "MemberRoleDTO [memberNo=" + memberNo + ", authorityNo=" + authorityNo + ", authority=" + authority
                + "]";
    }
}
