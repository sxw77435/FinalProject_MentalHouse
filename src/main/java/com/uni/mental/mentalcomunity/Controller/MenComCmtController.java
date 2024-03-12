package com.uni.mental.mentalcomunity.Controller;

import com.uni.mental.mentalcomunity.model.dto.MenComCmtDTO;
import com.uni.mental.mentalcomunity.model.dto.MenComDTO;
import com.uni.mental.mentalcomunity.model.service.MenComCmtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenComCmtController {


    private final MenComCmtService  menComCmtService;

    public MenComCmtController(MenComCmtService menComCmtService) {
        this.menComCmtService = menComCmtService;
    }

    @PostMapping("replyList")
    public void replyList(Model model) {

        List<MenComDTO> mencmtList = menComCmtService.mencmtList();
        System.out.println(mencmtList);
        model.addAttribute("mencmtList", mencmtList);
    }

    @RequestMapping(value = "/insertComment", method = {RequestMethod.POST})
    public String insertComment(Model model, @ModelAttribute MenComCmtDTO menComCmtDTO) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        menComCmtDTO.setId(id);

        menComCmtService.insertComment(menComCmtDTO);

        // 댓글을 삽입한 후에 댓글 리스트를 다시 조회하여 모델에 추가
        List<MenComCmtDTO> commentList = menComCmtService.getList(menComCmtDTO);
        model.addAttribute("commentList", commentList);

        System.out.println("commentList의 아이디입니다."+commentList);

        return "mencom/mental_detail :: #commentTable";
    }

    @RequestMapping(value = "deleteComment", method = {RequestMethod.POST})
    public String deleteComment(@RequestParam("commentNo")int no){

        menComCmtService.deleteComment(no);


        return "mencom/mental_detail :: #commentTable";
    }
    @GetMapping("/getCommentList")
    public String getCommentList(Model model, @RequestParam Integer menNo) {
        try {

            MenComCmtDTO menComCmtDTO = new MenComCmtDTO();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String id = authentication.getName();

            menComCmtDTO.setMenNo(menNo);
            List<MenComCmtDTO> commentList = menComCmtService.getList(menComCmtDTO);
            model.addAttribute("user", id);
            System.out.println("%%%%%"+id);
            model.addAttribute("commentList", commentList != null ? commentList : new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리
        }
        return "mencom/mental_detail :: #commentTable";
    }




}
