package com.uni.mental.mentalInfo.Controller;

import com.uni.mental.mentalInfo.model.dao.MentalDao;
import com.uni.mental.mentalInfo.model.dto.AttachDto;
import com.uni.mental.mentalInfo.model.dto.MentalDto;
import com.uni.mental.mentalInfo.model.service.MentalService;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

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

    @GetMapping("/info")
    public ModelAndView mentalInfoPage(ModelAndView mv) {
        List<MentalDto> mentalList = mentalService.findAllMental();

        // 根据每个MentalDto的attachno来获取attachoriname，并设置到MentalDto中
        for (MentalDto mentalDto : mentalList) {
            int attachNo = mentalDto.getAttachno(); // 获取附件号
            String attachoriname = mentalDao.getAttachOrinameByAttachNo(attachNo); // 根据附件号获取attachoriname
            mentalDto.setAttachoriname(attachoriname);
        }

        mv.addObject("mentalList", mentalList);
        mv.setViewName("mental/info");

        return mv;
    }
    @GetMapping("/detail")
    public String mentalDetailPage() {return "mental/detail";}

    @GetMapping("/insert")
    public String mentalInsertPage() {return "mental/insert";}

    @GetMapping("/update")
    public String mentalUpdatePage() {return "mental/update";}

    @PostMapping(value = "/insert", produces = MediaType.IMAGE_PNG_VALUE)
    public ModelAndView insertMental(@RequestParam("attachoriname") MultipartFile imageFile, @Validated MentalDto mentalDto, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // 处理图像文件上传
                String newFileName = generateUniqueFileName(imageFile.getOriginalFilename());

                // 1. 将照片保存到文件系统中
                String uploadDir = resourceLoader.getResource("classpath:/static/image").getFile().getAbsolutePath();
                File file = new File(uploadDir + File.separator + newFileName);
                imageFile.transferTo(file);

                // 2. 插入附件到tbl_attach表中
                AttachDto attachDto = new AttachDto();
                attachDto.setCatename("질환정보");
                attachDto.setAttachoriname(imageFile.getOriginalFilename());
                attachDto.setAttachnewname(newFileName);

                int attachNo = mentalDao.insertAttach(attachDto); // 插入附件并获取生成的attach_no

                // 3. 设置附加文件的 ATTACH_NO
                mentalDto.setAttachno(attachNo);

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
            } catch (Exception e) {
                modelAndView.setViewName("error");
                modelAndView.addObject("error", "등록실패：" + e.getMessage());
            }
        }

        return modelAndView;
    }private String generateUniqueFileName(String originalFileName) {
        // 在这里使用时间戳生成新的文件名
        long timestamp = System.currentTimeMillis();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return timestamp + extension;
    }






}
