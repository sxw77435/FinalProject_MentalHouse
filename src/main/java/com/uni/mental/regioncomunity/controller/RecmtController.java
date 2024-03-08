package com.uni.mental.regioncomunity.controller;

import com.uni.mental.regioncomunity.model.dto.RecomCmtDto;
import com.uni.mental.regioncomunity.model.dto.RecomDto;
import com.uni.mental.regioncomunity.model.service.RecmtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecmtController {

    private final RecmtService recmtService;

    public RecmtController(RecmtService recmtService) {
        this.recmtService = recmtService;
    }


    @PostMapping("recmtList")
    public void recmtList(Model model) {

        List<RecomDto> recmtList = recmtService.recmtList();
        System.out.println(recmtList);
        model.addAttribute("recmtList", recmtList);
    }

    @RequestMapping(value = "/insertRecmt", method = {RequestMethod.POST})
    public String insertRecmt(Model model, @ModelAttribute RecomCmtDto recomCmtDto) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberid = authentication.getName();
        recomCmtDto.setMemberid(memberid);

        recmtService.insertRecmt(recomCmtDto);

        // 댓글을 삽입한 후에 댓글 리스트를 다시 조회하여 모델에 추가
        List<RecomCmtDto> recmtList = recmtService.getRecmtList(recomCmtDto);
        model.addAttribute("recmtList", recmtList);

        System.out.println("commentList의 아이디입니다."+recmtList);

        return "recom/recomdetailview :: #commentTable";
    }

    @GetMapping("/getRecmtList")
    public String getRecmtList(Model model, @RequestParam Integer recomno) {
        try {

            RecomCmtDto recomCmtDto = new RecomCmtDto();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberid = authentication.getName();

            recomCmtDto.setRecomno(recomno);
            List<RecomCmtDto> recmtList = recmtService.getRecmtList(recomCmtDto);
            model.addAttribute("memberid", memberid);
            System.out.println("%%%%%"+memberid);
            model.addAttribute("recmtList", recmtList != null ? recmtList : new ArrayList<>());
            System.out.println("recmtList"+recmtList);

        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리
        }
        return "recom/recomdetailview :: #commentTable";
    }

    @RequestMapping(value = "deleteRecmt", method = {RequestMethod.POST})
    public ResponseEntity<String> deleteRecmt(@RequestParam("recmtno") int recmtno) {
        int rowsAffected = recmtService.deleteRecmt(recmtno);
        if (rowsAffected > 0) {
            return ResponseEntity.ok("Comment deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete comment.");
        }
    }
}
