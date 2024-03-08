package com.uni.mental.qna.controller;

import com.uni.mental.qna.model.dto.CommentDto;
import com.uni.mental.qna.model.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QnaComController {


    private final CommentService commentService;

    public QnaComController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("qnareplyList")
    public String qnareplyList(Model model) {
        List<CommentDto> qnareplyList = commentService.qnareplyList();
        System.out.println(qnareplyList);
        model.addAttribute("qnareplyList", qnareplyList);
        return "qna/qnaBoardDetailView";
    }

    @RequestMapping(value = "/insertReply", method = {RequestMethod.POST})
    public String insertReply(Model model, @ModelAttribute CommentDto commentDto) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            // 권한이 관리자가 아닌 경우 댓글 등록을 거부하고 홈 페이지로 리다이렉트
            return "redirect:/qna/qna";
        }

        String id = authentication.getName();
        commentDto.setId(id);

        commentService.insertReply(commentDto);

        // 댓글을 삽입한 후에 댓글 리스트를 다시 조회하여 모델에 추가
        List<CommentDto> commentList = commentService.getList(commentDto);
        model.addAttribute("commentList", commentList);

        System.out.println("commentList의 아이디입니다."+commentList);

        return "qna/qnaBoardDetailView :: #commentTable";
    }


    @RequestMapping(value = "deleteReply", method = {RequestMethod.POST})
    public String deleteReply(Model model, @RequestParam("pno") int pno) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            // 권한이 관리자가 아닌 경우 댓글 삭제를 거부하고 홈 페이지로 리다이렉트
            return "redirect:/qna/qna";
        }

        commentService.deleteReply(pno);

        return "qna/qnaBoardDetailView :: #commentTable";
    }

    @GetMapping("/getReplyList")
    public String getReplyList(Model model, @RequestParam Integer no) throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setNo(no);

        List<CommentDto> commentList = commentService.getList(commentDto);
        model.addAttribute("commentList", commentList);

        return "qna/qnaBoardDetailView :: #commentTable";
    }



}
