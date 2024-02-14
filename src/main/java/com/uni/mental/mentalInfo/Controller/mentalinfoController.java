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

@Controller
@RequestMapping("mental")
public class mentalinfoController {

    private final ResourceLoader resourceLoader;

    private final MentalService mentalService;
    private final MessageSource messageSource;

    private final MentalDao mentalDao;


    public mentalinfoController(MentalService mentalService, MessageSource messageSource, ResourceLoader resourceLoader, MentalDao mentalDao) {
        this.mentalService = mentalService;
        this.messageSource = messageSource;
        this.resourceLoader = resourceLoader;
        this.mentalDao = mentalDao;
    }

    @GetMapping("/insert")
    public String mentalInsertPage() {return "mental/insert";}

    @PostMapping(value = "/insert", produces = MediaType.IMAGE_PNG_VALUE)
    public ModelAndView insertMental(@RequestParam("attachnewname") MultipartFile imageFile, @Validated MentalDto mentalDto, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // 处理图像文件上传
                String newFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

                // 1. 将照片保存到文件系统中
                String uploadDir = resourceLoader.getResource("classpath:/static/image").getFile().getAbsolutePath();
                File file = new File(uploadDir + File.separator + newFileName);
                imageFile.transferTo(file);

                // 2. 插入附件到tbl_attach表中
                AttachDto attachDto = new AttachDto();
                attachDto.setAttachoriname(imageFile.getOriginalFilename());
                attachDto.setAttachnewname(newFileName);

                int attachresult = mentalDao.insertAttach(attachDto);

                if (attachresult > 0) {
                    int attachNo  = attachDto.getAttachno();

                    System.out.println("attachNo"+attachNo);

                    mentalDto.setAttachno(attachNo);
                    System.out.println(mentalDto.getAttachno());
                    
                    // 4. 插入数据到tbl_mentalinfo表中
                    int result = mentalDao.insertMental(mentalDto);

                    // 处理插入结果
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

    @GetMapping("/info")
    public ModelAndView findAllMental(ModelAndView mv) {
        List<MentalDto> mentalList = mentalService.findAllMental();

        // 根据每个MentalDto的attachno来获取attachoriname，并设置到MentalDto中
        mentalList.forEach(mentalDto -> System.out.println("mentalDto=" + mentalDto));

        mv.addObject("mentalList", mentalList);

        mv.setViewName("mental/info");

        return mv;
    }

    @GetMapping("/detail/{mentalinfono}")
    public String findMentalByCode(@PathVariable int mentalinfono, Model model) {

        MentalDto mentalDto = mentalService.findMentalByCode(mentalinfono);


        model.addAttribute("mentalDto", mentalDto);


        return "mental/detail";
    }

    @GetMapping("/update/{mentalinfono}")
    public ModelAndView mentalUpdatePage(@PathVariable("mentalinfono") int mentalinfono, ModelAndView mv) {
        mv.addObject("mentalDto", mentalService.findMentalByCode(mentalinfono))
                .setViewName("mental/update");
        return mv;
    }

    @PostMapping(value = "/update", produces = MediaType.IMAGE_PNG_VALUE)
    public ModelAndView updateMental(
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @Validated MentalDto mentalDto,
            BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        try {
            // 处理图像文件上传
            if (imageFile != null && !imageFile.isEmpty()) {
                // 生成唯一的文件名
                String attachnewname = UUID.randomUUID().toString() + ".png";

                // 获取文件保存路径
                String uploadDir = resourceLoader.getResource("classpath:/static/image").getFile().getAbsolutePath();

                // 将文件保存到文件系统中
                File file = new File(uploadDir, attachnewname);
                imageFile.transferTo(file);

                // 删除旧图片
                String oldImageFileName = mentalDto.getAttachnewname();
                if (oldImageFileName != null && !oldImageFileName.isEmpty()) {
                    File oldFile = new File(uploadDir, oldImageFileName);
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                // 更新TBL_ATTACH表中的ATTACH_NEWNAME字段

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

            // 更新TBL_MENTALINFO表中的其他字段
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

        // 将 mentalDto 放入模型中
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