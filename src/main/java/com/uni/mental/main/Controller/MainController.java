package com.uni.mental.main.Controller;

import com.uni.mental.main.model.dto.MainDTO;
import com.uni.mental.main.model.service.MainService;
import com.uni.mental.notice.model.dto.NoticeDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.ui.Model;

import java.util.List;

@Controller
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping(value={"/", "/main", "/main/main"})
    public String main(Model model) {
        List<MainDTO> posts = mainService.getAllPostsOrderByDateDesc();
        List<NoticeDTO> notices = mainService.getNotices();  // 공지사항 데이터 가져오기
        model.addAttribute("posts", posts);
        model.addAttribute("notices", notices);  // 공지사항 데이터 모델에 추가
        return "main/main";
    }

    @PostMapping(value="/")
    public String redirectMain(){return "redirect:/";}


}
