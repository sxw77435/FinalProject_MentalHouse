package com.uni.mental.qna.controller;

import com.uni.mental.qna.model.dao.QnaDao;
import com.uni.mental.qna.model.dto.QnaDto;
import com.uni.mental.qna.model.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("qna")
public class QnaController {

    private final QnaService qnaService;
    private final QnaDao qnaDao;



    @Autowired
    public QnaController(QnaService qnaService, QnaDao qnaDao) {
        this.qnaService = qnaService;

        this.qnaDao = qnaDao;
    }

    @GetMapping("/qna") // 리스트
    public void showQna(Model model) {

        List<QnaDto> qnaList = qnaService.getAllQna();
        System.out.println(qnaList);
        // 공지사항 데이터를 가져와서 모델에 추가
        model.addAttribute("qnas", qnaList);

    }



    @GetMapping("/qnaBoardEnroolForm")
    public String showQnaBoardEnrollForm() {
        // 공지사항 등록 폼 뷰로 이동
        return "qna/qnaBoardEnroolForm";
    }

    @PostMapping("/regist")
    public String handleRegist(QnaDto qnaDto) {
        // POST 요청 처리 로직 작성


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        qnaDto.setId(id);

        qnaService.addQna(qnaDto);


        return "redirect:/qna/qna"; // 처리 후 리다이렉트할 경로 반환
    }


    @GetMapping("qnaBoardDetailView")
    public ModelAndView showQnaDetail(@RequestParam("no") String no, ModelAndView mv
    ){
        QnaDto qna = qnaService.selectOne(Integer.parseInt(no));



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        mv.addObject("user",id);
        System.out.println("~~~~~~~~~~~~~~~~~"+id);


        mv.addObject("qna",qna);

        System.out.println(qna);
        mv.setViewName("qna/qnaBoardDetailView");

        return mv;
    }

    @GetMapping("qnaUpdateForm") // 댓글 관련
    public ModelAndView QnaUpdateForm(@RequestParam("no") String no, ModelAndView mv){
        QnaDto qna = qnaService.selectOne(Integer.parseInt(no));

        mv.addObject("qna",qna);
        mv.setViewName("qna/qnaUpdateForm");

        return mv;
    }
    @PostMapping("qnaupdate") // qna 수정
    public String QnaUpdate(@ModelAttribute QnaDto qnaupdate) throws Exception {

        qnaService.updateQna(qnaupdate);

        return "redirect:/qna/qna";
    }
//    @GetMapping("qnaUpdateForm") //공지사항 수정 폼으로 이동
//    public ModelAndView QnaUpdateForm(@RequestParam("no") String no, ModelAndView mv){
//        QnaDto qna = qnaService.selectOne(Integer.parseInt(no));
//
//        mv.addObject("qna",qna);
//        mv.setViewName("qna/noticeUpdateForm");
//
//        return mv;
//    }

    @PostMapping("/delete") //Qna 삭제
    public String handleDelete(@RequestParam("qnaNo") int qnaNo) {
        // 삭제할 공지사항 번호를 받아와서 해당 공지사항을 삭제하는 로직 작성
        qnaService.deleteQna(Math.toIntExact(qnaNo));


        return "redirect:/qna/qna"; // 처리 후 리다이렉트할 경로 반환
    }





}

