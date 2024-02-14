package com.uni.mental.ageComunity.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import com.uni.mental.ageComunity.model.dao.AgeComDAO;
import com.uni.mental.ageComunity.model.dto.AgeComDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public String AgeComList(Model model) {
        List<AgeComDTO> AgeComList = ageComDAO.findAllView();
        //로그출력하기!!
        logger.info("AgeComList: {}", AgeComList);
        model.addAttribute("AgeComList", AgeComList);
        return "agecom/AgeComList";
    }


    @GetMapping("AgeComEnrollForm")
    public void AgeComEnrollForm(){}

    @GetMapping("AgeComUpdateForm")
    public ModelAndView AgeComUpdateForm(@RequestParam("no") String no, ModelAndView mv){
        AgeComDTO ageComDTO = ageComDAO.selectOne(Integer.parseInt(no));

        mv.addObject("ageComDTO",ageComDTO);
        mv.setViewName("agecom/AgeComUpdateForm");

        return mv;
    }
    @GetMapping("AgeComDetailView")
    public ModelAndView AgeComDetailView(@RequestParam("no") String no, ModelAndView mv){
        AgeComDTO ageComDTO = ageComDAO.selectOne(Integer.parseInt(no));

        mv.addObject("ageComDTO",ageComDTO);
        mv.setViewName("agecom/AgeComDetailView");

        return mv;
    }

    @PostMapping("regist")
    public String AgeComRegist(@ModelAttribute AgeComDTO ageCom){
        ageCom.setMemberNick("dndjd");
        ageComDAO.registAgeCom(ageCom);
        return "redirect:/agecom/AgeComList";
    }

    @PostMapping("update")
    public String AgeComUpdate(@ModelAttribute AgeComDTO ageCom){

        ageComDAO.updateAgeCom(ageCom);

        return "redirect:/agecom/AgeComList";
    }

    @PostMapping("delete")
    public String AgeComDelete(@RequestParam("no") int no){
        ageComDAO.deleteAgeCom(no);

        return "redirect:/agecom/AgeComList";
    }

}
