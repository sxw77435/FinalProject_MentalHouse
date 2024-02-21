package com.uni.mental.agecomunity.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.uni.mental.agecomunity.model.dao.AgeComDAO;
import com.uni.mental.agecomunity.model.dto.AgeComDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/agecom")
public class AgeComController {
    //로그출력하기!!
    private static final Logger logger = LoggerFactory.getLogger(AgeComController.class);
    private final AgeComDAO ageComDAO;

    public AgeComController(AgeComDAO ageComDAO) {
        this.ageComDAO = ageComDAO;
    }

    @GetMapping("/AgeComList")
    public void AgeComList(Model model) {
        List<AgeComDTO> AgeComList = ageComDAO.findAllView();
        //로그출력하기!!
        logger.info("AgeComList: {}", AgeComList);
//        model.addAttribute("AgeComList", AgeComList != null ? AgeComList : new ArrayList<>());
      model.addAttribute("AgeComList", AgeComList);
    }


    @GetMapping("/AgeComEnrollForm")
    public void AgeComEnrollForm(){}

    @GetMapping("/AgeComUpdateForm")
    public ModelAndView AgeComUpdateForm(@RequestParam("no") String no, ModelAndView mv){
        AgeComDTO ageComDTO = ageComDAO.selectOne(Integer.parseInt(no));
        mv.addObject("ageComDTO",ageComDTO);
        mv.setViewName("agecom/AgeComUpdateForm");
        return mv;
    }
    @GetMapping("/AgeComDetailView")
    public ModelAndView AgeComDetailView(@RequestParam("no") String no, ModelAndView mv){
        AgeComDTO ageComDTO = ageComDAO.selectOne(Integer.parseInt(no));
        mv.addObject("ageComDTO",ageComDTO);
        mv.setViewName("agecom/AgeComDetailView");
        return mv;
    }

    @PostMapping("/regist")
    public String AgeComRegist(@ModelAttribute AgeComDTO ageComDTO){
        ageComDTO.setMemberNick("dndjd");
        ageComDAO.registAgeCom(ageComDTO);
        return "redirect:/agecom/AgeComList";
    }

    @PostMapping("/update")
    public String AgeComUpdate(@ModelAttribute AgeComDTO ageComDTO){
        ageComDAO.updateAgeCom(ageComDTO);
        return "redirect:/agecom/AgeComList";
    }

    @PostMapping("/delete")
    public String AgeComDelete(@RequestParam("no") int no){
        ageComDAO.deleteAgeCom(no);
        return "redirect:/agecom/AgeComList";
    }

}
