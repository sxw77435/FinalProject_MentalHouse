package com.uni.mental.member.Controller;

import com.uni.mental.member.model.dao.MemberDao;
import com.uni.mental.member.model.dto.MemberDto;
import com.uni.mental.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("login")
public class MemberController {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberService memberService;




    @GetMapping("/loginpage")
    public void memberLoginForm(){}

    @GetMapping("/agreement")
    public String agreement() {
        return "login/agreement";
    }

    @GetMapping("/enroll")
    public String enrollForm() {
        return "login/enroll";
    }

    @PostMapping("/enroll")
    public String enrollMember(@RequestParam("birthYear") String birthYear,
                               @RequestParam("birthMonth") String birthMonth,
                               @RequestParam("birthDate") String birthDate,
                               @RequestParam("post") String post,
                               @RequestParam("address1") String address1,
                               @RequestParam("address2") String address2,
                               MemberDto memberDto,
                               Model model) {
        // 주소 처리
        String fullAddress = post + "/" + address1 + "/" + address2;
        memberDto.setAdd(fullAddress);

        // 생일 타입 처리
        String birthString = birthYear + "-" + birthMonth + "-" + birthDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday = LocalDate.parse(birthString, formatter);
        java.sql.Date sqlDate = java.sql.Date.valueOf(birthday);
        memberDto.setBir(sqlDate);

        String encodedPassword = passwordEncoder.encode(memberDto.getPwd());
        memberDto.setPwd(encodedPassword);

        // 데이터 베이스에 저장
        memberDao.enrollMember(memberDto);

        model.addAttribute("memberDto", memberDto);

        System.out.println("memberDto: " + memberDto);

        return "redirect:/login/success";
    }

    @ResponseBody
    @RequestMapping("idCheck")
    public String idCheck(String id) {

        int count = memberService.idCheck(id);

        return String.valueOf(count);
    }

    @ResponseBody
    @RequestMapping("nicknameCheck")
    public String nicknameCheck(String nick) {

        int count = memberService.nicknameCheck(nick);

        return String.valueOf(count);
    }
    @GetMapping("/success")
    public String enrollsuccess() {return "login/success";}


}
