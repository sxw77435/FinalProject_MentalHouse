package com.uni.mental.notice.controller;

import com.uni.mental.notice.model.dto.NoticeDTO;
import com.uni.mental.notice.model.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("notice")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notice")
    public String showNotice(Model model) {
        // 공지사항 데이터를 번호를 기준으로 오름차순으로 정렬하여 가져와서 모델에 추가
        List<NoticeDTO> notices = noticeService.getAllNotices(); // 번호를 기준으로 오름차순으로 정렬된 공지사항 데이터를 가져옴
        model.addAttribute("notices", notices);

        return "notice/notice"; // notice.html로 이동
    }



    @GetMapping("/noticeBoardEnrollForm")
    public String showNoticeBoardEnrollForm() {
        // 공지사항 등록 폼 뷰로 이동
        return "notice/noticeBoardEnrollForm";
    }

    @PostMapping("/regist")
    public String handleRegist(NoticeDTO noticeDto) {
        // POST 요청 처리 로직 작성
        noticeService.addNotice(noticeDto);


        return "redirect:/notice/notice"; // 처리 후 리다이렉트할 경로 반환
    }

    @PostMapping("/delete") //공지사항 삭제
    public String handleDelete(@RequestParam("noticeNo") int noticeNo) {
        // 삭제할 공지사항 번호를 받아와서 해당 공지사항을 삭제하는 로직 작성
        noticeService.deleteNotice(Math.toIntExact(noticeNo));


        return "redirect:/notice/notice"; // 처리 후 리다이렉트할 경로 반환
    }

    @GetMapping("/noticeBoardDetailView")
    public String showNoticeDetail(@RequestParam("no") String no, Model model) {
        System.out.println(no);
        NoticeDTO noticeDTO = noticeService.getNoticeById(Integer.parseInt(no));
        System.out.println(noticeDTO);
        model.addAttribute("notice", noticeDTO);
        return "notice/noticeBoardDetailView"; // 뷰의 이름 반환
    }
    @PostMapping("noticeupdate") // 공지사항 수정
    public String NoticeUpdate(@ModelAttribute NoticeDTO noticeupdate) throws Exception {

        noticeService.updateNotice(noticeupdate);

        return "redirect:/notice/notice";
    }
    @GetMapping("noticeUpdateForm") //공지사항 수정 폼으로 이동
    public ModelAndView NoticeUpdateForm(@RequestParam("no") String no, ModelAndView mv){
        NoticeDTO notice = noticeService.selectOne(Integer.parseInt(no));

        mv.addObject("notice",notice);
        mv.setViewName("notice/noticeUpdateForm");

        return mv;
    }






}

