package com.uni.mental.mentalComunity.Controller;

import com.uni.mental.mentalComunity.model.dao.MenComDAO;
import com.uni.mental.mentalComunity.model.dto.MenComDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/mencom")
public class MenComController {

    private final MenComDAO menComDAO;

    public MenComController(MenComDAO menComDAO) {
        this.menComDAO = menComDAO;
    }

    @GetMapping("mentalList")
    public void MentalList(Model model, Pageable pageable) {
        List<MenComDTO> mentalList = menComDAO.findAllView();

        model.addAttribute("mentalList", mentalList);
    }


    @GetMapping("mentalEnrollForm")
    public void MentalEnrollForm(){}

    @GetMapping("mentalUpdateForm")
    public ModelAndView MentalUpdateForm(@RequestParam("no") String no, ModelAndView mv){
        MenComDTO mental = menComDAO.selectOne(Integer.parseInt(no));

        mv.addObject("mental",mental);
        mv.setViewName("mencom/mentalUpdateForm");

        return mv;
    }
    @GetMapping("mentalDetailView")
    public ModelAndView MentalDetailView(@RequestParam("no") String no, ModelAndView mv){
        MenComDTO mental = menComDAO.selectOne(Integer.parseInt(no));

        mv.addObject("mental",mental);
        mv.setViewName("mencom/mentalDetailView");

        return mv;
    }

    @PostMapping("regist")
    public String MentalRegist(@ModelAttribute MenComDTO menCom){

        menCom.setNickName("dndjd");
        menComDAO.registMenCom(menCom);


        return "redirect:/mencom/mentalList";

    }
    @PostMapping("update")
    public String MentalUpdate(@ModelAttribute MenComDTO menCom){

        menComDAO.updateMencom(menCom);

        return "redirect:/mencom/mentalList";
    }

    @PostMapping("delete")
    public String MentalDelete(@RequestParam("no") int no){
        menComDAO.deleteMencom(no);

        return "redirect:/mencom/mentalList";
    }

}
