package com.uni.mental.agecommunity.Controller;

import com.uni.mental.agecommunity.model.dao.AgeComDAO;
import com.uni.mental.agecommunity.model.dto.AgeCmtDTO;
import com.uni.mental.agecommunity.model.service.AgeCmtService;
import com.uni.mental.agecommunity.model.service.AgeComService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/agecom")
public class AgeCmtController {

    private final AgeCmtService ageCmtService;
    private final AgeComService ageComService; // AgeComService를 추가합니다.
    private final AgeComDAO ageComDAO;

    @Autowired
    public AgeCmtController(AgeCmtService ageCmtService, AgeComService ageComService, AgeComDAO ageComDAO) {
        this.ageCmtService = ageCmtService;
        this.ageComService = ageComService; // 생성자에 AgeComService를 주입합니다.
        this.ageComDAO = ageComDAO;
    }

    @PostMapping("/addComment")
    public String addComment(AgeCmtDTO comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String memberNick = authentication.getName();
            comment.setMemberNick(memberNick);
            ageCmtService.addComment(comment);
            return "redirect:/agecom/AgeComDetailView/" + comment.getAgeComNo();
        } else {
            return "redirect:/login";
        }
    }




    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam("ageCmtNo") int ageCmtNo, @RequestParam("ageComNo") int ageComNo) {
        ageCmtService.removeComment(ageCmtNo);
        return "redirect:/agecom/AgeComDetailView/" + ageComNo; // 삭제 후 댓글이 속한 게시글 상세 페이지로 리다이렉트
    }

    @PostMapping("/updateReplyCount")
    public String updateReplyCount(@RequestParam("ageComNo") int ageComNo) {
        ageComService.updateReplyCount(ageComNo);
        return "redirect:/agecom/AgeComDetailView/" + ageComNo;
    }

}

