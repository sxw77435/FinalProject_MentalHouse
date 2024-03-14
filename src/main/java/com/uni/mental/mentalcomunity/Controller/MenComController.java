package com.uni.mental.mentalcomunity.Controller;

import com.uni.mental.mentalcomunity.model.dto.MenComDTO;
import com.uni.mental.mentalcomunity.model.service.MenComService;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/mencom")
public class MenComController {



    private final ResourceLoader resourceLoader;

    private final MenComService menComService;

    public MenComController(ResourceLoader resourceLoader, MenComService menComService) {
        this.resourceLoader = resourceLoader;
        this.menComService = menComService;
    }

    @GetMapping("mental_list")
    public void MentalList(Model model,
                            @RequestParam(name="cate",required = false ) Integer cate) {
        List<MenComDTO> mentalList;

        if (cate != null) {
            // CATENO따라서 리스트 나오기
            mentalList = menComService.findAllCate(cate);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@"+mentalList);
        } else {
            mentalList = menComService.findAllView();
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!"+mentalList);
        }

        model.addAttribute("cate", cate);
        model.addAttribute("mentalList", mentalList);
    }


    @GetMapping("mental_enroll")
    public void MentalEnrollForm(){}

    @GetMapping("mental_update")
    public ModelAndView MentalUpdateForm(@RequestParam("no") String no, ModelAndView mv){
        MenComDTO mental = menComService.selectOne(Integer.parseInt(no));

        mv.addObject("mental",mental);
        mv.setViewName("mencom/mental_update");

        return mv;
    }
    @GetMapping("mental_detail")
    public ModelAndView MentalDetail(@RequestParam("no") String no, ModelAndView mv
                                         ){
        MenComDTO mental = menComService.selectOne(Integer.parseInt(no));
        int updateViews = menComService.updateViews(Integer.parseInt(no));


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        mv.addObject("user", id);
        mv.addObject("updateViews", updateViews);
        mv.addObject("mental", mental);

        mv.setViewName("mencom/mental_detail");

        return mv;
    }

    @PostMapping(value = "regist",produces = MediaType.IMAGE_PNG_VALUE)
    public ModelAndView MentalRegist(@RequestParam(value = "image",required = false) MultipartFile uploadFile,
                                     @ModelAttribute @Validated MenComDTO menCom,
                                     BindingResult bindingResult
                                    ) {

        System.out.println(uploadFile);
        ModelAndView modelAndView = new ModelAndView();

        try {
           
            if (uploadFile != null && !uploadFile.isEmpty()) {
                String imageFileName = UUID.randomUUID().toString() + "_" + uploadFile.getOriginalFilename();
                String uploadDir = resourceLoader.getResource("classpath:/static/image").getFile().getAbsolutePath();
                File file = new File(uploadDir + File.separator + imageFileName);
                uploadFile.transferTo(file);

                menCom.setImage(imageFileName);
                System.out.println(imageFileName);
                System.out.println(uploadDir);


                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String id = authentication.getName();
                menCom.setId(id);



                int result = menComService.registMenCom(menCom);

                if (result > 0) {
                    modelAndView.setViewName("redirect:/mencom/mental_list");
                    modelAndView.addObject("message", "등록 성공");
                } else {
                    modelAndView.setViewName("error");
                    modelAndView.addObject("error", "등록 실패");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
            modelAndView.addObject("error", "등록 실패");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return modelAndView;
    }


    @PostMapping(value = "update", produces = MediaType.IMAGE_PNG_VALUE)
    public ModelAndView MentalUpdate(@RequestParam(value = "image", required = false) MultipartFile uploadFile,
                                     @Validated MenComDTO menCom,
                                     BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            if (uploadFile != null && !uploadFile.isEmpty()) {
                String imageFileName = UUID.randomUUID().toString() + "_" + uploadFile.getOriginalFilename();
                String uploadDir = resourceLoader.getResource("classpath:/static/image").getFile().getAbsolutePath();
                File file = new File(uploadDir + File.separator + imageFileName);
                uploadFile.transferTo(file);

                String oldImageFileName = menCom.getImage();
                if (oldImageFileName != null && !oldImageFileName.isEmpty()) {
                    File oldFile = new File(uploadDir + File.separator + oldImageFileName);
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                menCom.setImage(imageFileName);
            }

            if (uploadFile == null || uploadFile.isEmpty()) {
                MenComDTO originalDto = menComService.selectOne(menCom.getNo());
                if (originalDto != null) {
                    menCom.setImage(originalDto.getImage());
                }
            }

            menComService.updateMencom(menCom);

            modelAndView.setViewName("redirect:/mencom/mental_list");
            modelAndView.addObject("message", "업데이트 성공");
        } catch (IOException e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
            modelAndView.addObject("error", "업데이트 실패");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        modelAndView.addObject("menCom", menCom);

        return modelAndView;
    }




    @PostMapping("delete")
public String MentalDelete(@RequestParam("no") int no){
    menComService.deleteMencom(no);

    return "redirect:/mencom/mental_list";
}




}
