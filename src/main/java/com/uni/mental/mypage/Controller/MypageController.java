package com.uni.mental.mypage.Controller;

import com.uni.mental.mypage.model.dao.MypageDao;
import com.uni.mental.mypage.model.dto.MypageDto;
import com.uni.mental.mypage.model.service.MypageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("mypage")
public class MypageController {

    private final MypageService mypageService;

    private MypageDao mypageDao;

    public MypageController(MypageService mypageService, MypageDao mypageDao) {
        this.mypageService = mypageService;
        this.mypageDao = mypageDao;
    }


    @GetMapping("/mypage") // 리스트
    public String showMypage(Model model, HttpServletRequest request) {
        // 인증 객체를 이용하여 사용자가 로그인되어 있는지 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            // 사용자가 로그인되어 있지 않은 경우, 로그인 페이지로 리디렉션
            return "redirect:/login/loginpage";
        }

        // 로그인된 사용자의 아이디 가져오기
        String id = authentication.getName();

//        // 추가적인 비밀번호 인증이 필요한지 여부 확인
//        if (pwd(id)) {
//            // 추가적인 비밀번호 인증이 필요한 경우, 비밀번호 인증 페이지로 리디렉션
//            return "redirect:/mypage/mypagepwd";
//        }

        // 마이페이지 데이터를 가져와서 모델에 추가
        List<MypageDto> mypageList = mypageService.getMypageById(id);
        System.out.println(mypageList);
        model.addAttribute("members", mypageList);

        // 마이페이지 뷰로 이동
        return "mypage/mypage";
    }

    @PostMapping("/delete") // 회원탈퇴
    public String handleDelete(@RequestParam("mypageNo") int mypageNo, HttpSession session) {
        // 탈퇴할 회원번호를 가져와서 탈퇴
        mypageService.deleteMypage(Math.toIntExact(mypageNo));

        // 세션 무효화를 통해 로그아웃 처리
        session.invalidate();

        return "redirect:/main"; // 메인 페이지로 리다이렉트
    }

    @GetMapping("/mypageBoardDetailView/{no}")
    public String showMypageDetail(@PathVariable("no") String no, Model model) {
        System.out.println(no);
        MypageDto mypageDto = mypageService.mypageDetail(Integer.parseInt(no));
        System.out.println(mypageDto);
        model.addAttribute("mypage", mypageDto);
        return "mypage/mypageBoardDetailView"; // 뷰의 이름 반환
    }



    @PostMapping("update") // 공지사항 수정
    public String MypageUpdate(@ModelAttribute MypageDto mypageupdate, Model model){

        mypageService.updateMypage(mypageupdate);

        return "redirect:/mypage/mypage";
    }







}
