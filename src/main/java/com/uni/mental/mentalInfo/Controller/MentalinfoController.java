package com.uni.mental.mentalInfo.Controller;

import com.uni.mental.mentalInfo.model.dao.MentalDao;
import com.uni.mental.mentalInfo.model.dto.AttachDto;
import com.uni.mental.mentalInfo.model.dto.MentalDto;
import com.uni.mental.mentalInfo.model.service.MentalService;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

// 정신정보 기능
@Controller
@RequestMapping("mental")
public class MentalinfoController {

    private final ResourceLoader resourceLoader;

    private final MentalService mentalService;
    private final MessageSource messageSource;


    private final MentalDao mentalDao;


    public MentalinfoController(MentalService mentalService, MessageSource messageSource, ResourceLoader resourceLoader, MentalDao mentalDao) {
        this.mentalService = mentalService;
        this.messageSource = messageSource;
        this.resourceLoader = resourceLoader;
        this.mentalDao = mentalDao;
    }

    // insert화면 나오기 , 추가 기능 하기(젤 먼저 한 기능)
    @GetMapping("/insert")
    public String mentalInsertPage() {return "mental/insert";}

    // insert할때 사진 관련 처리
    @PostMapping(value = "/insert", produces = MediaType.IMAGE_PNG_VALUE)
    public ModelAndView insertMental(@RequestParam("attachnewname") MultipartFile imageFile, @Validated MentalDto mentalDto, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // 사진 파일 올리기 새 이름 새성
                String newFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

                // 1. 사진을 시스템에 저장
                String uploadDir = resourceLoader.getResource("classpath:/static/image").getFile().getAbsolutePath();
                File file = new File(uploadDir + File.separator + newFileName);
                imageFile.transferTo(file);

                // 2. attach파일 따로 있을때 원래 이름이랑 새이름 저장
                AttachDto attachDto = new AttachDto();
                attachDto.setAttachoriname(imageFile.getOriginalFilename());
                attachDto.setAttachnewname(newFileName);

                int attachresult = mentalDao.insertAttach(attachDto);

                if (attachresult > 0) {
                    int attachNo  = attachDto.getAttachno();

                    System.out.println("attachNo"+attachNo);

                    mentalDto.setAttachno(attachNo);
                    System.out.println(mentalDto.getAttachno());
                    
                    // 4. 데이터를 tbl_attach에 저장
                    int result = mentalDao.insertMental(mentalDto);


                    if (result > 0) {
                        modelAndView.setViewName("redirect:/mental/info");
                        modelAndView.addObject("message", "등록 성공");
                    } else {
                        modelAndView.setViewName("error");
                        modelAndView.addObject("error", "등록 실패");
                    }
                } else {
                    modelAndView.setViewName("error");
                    modelAndView.addObject("error", "등록실패");
                }
            } catch (Exception e) {
                modelAndView.setViewName("error");
                modelAndView.addObject("error", "등록 실패：" + e.getMessage());
            }
        }

        return modelAndView;
    }


//    }private String generateUniqueFileName(String originalFileName) {
//        // 在这里使用时间戳生成新的文件名
//        long timestamp = System.currentTimeMillis();
//        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
//        return timestamp + extension;
//    }

    // select 리스트 기능
    @GetMapping("/info")
    public ModelAndView findAllMental(ModelAndView mv) {
        List<MentalDto> mentalList = mentalService.findAllMental();

        // MentalDto중에 있는 attachNo데이터를 가지고 해당 attachNewName데이터를 가지오기
        mentalList.forEach(mentalDto -> System.out.println("mentalDto=" + mentalDto));

        mv.addObject("mentalList", mentalList);

        mv.setViewName("mental/info");

        return mv;
    }

    //상세 페이지
    @GetMapping("/detail/{mentalinfono}")
    public String findMentalByCode(@PathVariable int mentalinfono, Model model) {

        MentalDto mentalDto = mentalService.findMentalByCode(mentalinfono);


        model.addAttribute("mentalDto", mentalDto);


        return "mental/detail";
    }

    //업데이터 화면

    @GetMapping("/update/{mentalinfono}")
    public ModelAndView mentalUpdatePage(@PathVariable("mentalinfono") int mentalinfono, ModelAndView mv) {
        mv.addObject("mentalDto", mentalService.findMentalByCode(mentalinfono))
                .setViewName("mental/update");
        return mv;
    }

    // 업데이터 관련 사진 로직
    @PostMapping(value = "/update", produces = MediaType.IMAGE_PNG_VALUE)
    public ModelAndView updateMental(
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @Validated MentalDto mentalDto,
            BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        try {

            if (imageFile != null && !imageFile.isEmpty()) {

                String attachnewname = UUID.randomUUID().toString() + ".png";

                String uploadDir = resourceLoader.getResource("classpath:/static/image").getFile().getAbsolutePath();

                File file = new File(uploadDir, attachnewname);
                imageFile.transferTo(file);

                // 전 사진 삭제
                String oldImageFileName = mentalDto.getAttachnewname();
                if (oldImageFileName != null && !oldImageFileName.isEmpty()) {
                    File oldFile = new File(uploadDir, oldImageFileName);
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                // TBL_ATTACH의ATTACH_NEWNAME 업데이터

                AttachDto attachDto = new AttachDto();
                attachDto.setAttachno(mentalDto.getMentalinfono()); // notion！
                attachDto.setAttachnewname(attachnewname);
                System.out.println(attachnewname);
                String attachOriname = imageFile.getOriginalFilename();
                attachDto.setAttachoriname(attachOriname);
                int attachUpdateResult = mentalService.updateAttach(attachDto);
                if (attachUpdateResult <= 0) {
                    throw new RuntimeException("Failed to update ATTACH_NEWNAME in TBL_ATTACH table");
                }
            }

            // TBL_MENTALINFO의 다른 컬럼 업데이터
            int result = mentalService.updateMental(mentalDto);
            if (result > 0) {
                modelAndView.setViewName("redirect:/mental/info");
                modelAndView.addObject("message", "업데이트 성공");
            } else {
                modelAndView.setViewName("error");
                modelAndView.addObject("error", "업데이트 실패");
            }
        } catch (IOException e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
            modelAndView.addObject("error", "업데이트 실패");
        }

       
        modelAndView.addObject("mental", mentalDto);
        return modelAndView;
    }



    @DeleteMapping("/delete/{mentalinfono}")
    public ResponseEntity<String> deleteMental(@PathVariable int mentalinfono) {
        try {
            // 删除心理信息
            mentalService.deleteMental(mentalinfono);

            // 删除相关联的照片
            mentalService.deleteAttach(mentalinfono);

            return ResponseEntity.ok("Mental deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete mental: " + e.getMessage());
        }
    }
    @GetMapping("/search")
    public @ResponseBody List<MentalDto> searchMentalByName(@RequestParam("mentalname") String mentalname) {
        return mentalService.searchMentalByKeyword("%" + mentalname + "%");
    }






}