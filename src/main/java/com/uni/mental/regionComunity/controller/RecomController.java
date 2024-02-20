package com.uni.mental.regionComunity.controller;


import com.uni.mental.regionComunity.model.dao.RecomDao;
import com.uni.mental.regionComunity.model.dto.RecomDto;
import com.uni.mental.regionComunity.model.service.RecomService;
import org.springframework.context.MessageSource;
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
@RequestMapping("recom")
public class RecomController {

    private final ResourceLoader resourceLoader;

    private final RecomService recomService;
    private final MessageSource messageSource;

    private final RecomDao recomDao;


    public RecomController(RecomService recomService, MessageSource messageSource, ResourceLoader resourceLoader, RecomDao recomDao) {
        this.recomService = recomService;
        this.messageSource = messageSource;
        this.resourceLoader = resourceLoader;
        this.recomDao = recomDao;
    }

    @GetMapping("/recomenroll")
    public void recomInsertPage() {}

    @PostMapping(value="/recomenroll" , produces = MediaType.IMAGE_PNG_VALUE)
    public ModelAndView insertRecom(@RequestParam("recomimage") MultipartFile imageFile,
                                    @Validated RecomDto recomDto,
                                    BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            // 处理图像文件上传
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                String uploadDir = resourceLoader.getResource("classpath:/static/image").getFile().getAbsolutePath();
                File file = new File(uploadDir + File.separator + imageFileName);
                imageFile.transferTo(file);

                recomDto.setRecomimage(imageFileName);

                // 从安全上下文中获取当前已认证的用户的用户名
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String memberId = authentication.getName();
                recomDto.setMemberid(memberId);

                // 设置初始的社区观看数（如果需要）
                recomDto.setRecomviews(0);

                // 插入数据到数据库
                int result = recomDao.insertRecom(recomDto);

                if (result > 0) {

                    modelAndView.setViewName("redirect:/recom/recomlist");

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
        }

        return modelAndView;
    }

    @GetMapping("/recomlist")
    public ModelAndView findAllRecom(@RequestParam(name = "cateno", required = false) Integer cateno, ModelAndView mv) {
        List<RecomDto> recomList;
        if (cateno != null) {
            // 根据 cateno 参数过滤推荐内容
            recomList = recomService.findRecomByCateno(cateno);
        } else {
            // 如果未提供 cateno 参数，则获取所有推荐内容
            recomList = recomService.findAllRecom();
        }

        recomList.forEach(recom -> System.out.println("recom=" + recom));

        mv.addObject("recomList", recomList);
        mv.setViewName("recom/recomlist");

        return mv;
    }


    @GetMapping("/recomdetailview/{recomno}")
    public String findRecomByCode(@PathVariable int recomno, Model model) {

        RecomDto recomDto = recomService.findRecomByCode(recomno);
        recomService.updateRecomViews(recomno, recomDto.getRecomviews() + 1); // 增加浏览次数

        model.addAttribute("recomDto", recomDto);


        return "recom/recomdetailview";
    }

    @GetMapping("/recomupdate/{recomno}")
    public ModelAndView updateRecomForm(@PathVariable("recomno") int recomno, ModelAndView mv) {
        mv.addObject("recomDto", recomService.findRecomByCode(recomno))
                .setViewName("recom/recomupdate");

        return mv;
    }

    @PostMapping(value = "/recomupdate", produces = MediaType.IMAGE_PNG_VALUE)
    public ModelAndView updateRecom(
            @RequestParam(value = "recomimage", required = false) MultipartFile imageFile,
            @Validated RecomDto recomDto,
            BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            // 处理图像文件上传
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                String uploadDir = resourceLoader.getResource("classpath:/static/image").getFile().getAbsolutePath();
                File file = new File(uploadDir + File.separator + imageFileName);
                imageFile.transferTo(file);

                // 删除旧照片
                String oldImageFileName = recomDto.getRecomimage();
                if (oldImageFileName != null && !oldImageFileName.isEmpty()) {
                    File oldFile = new File(uploadDir + File.separator + oldImageFileName);
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                recomDto.setRecomimage(imageFileName);
            }

            // 检查是否有新图片上传，如果没有，保留原始图片
            if (imageFile == null || imageFile.isEmpty()) {
                // 获取原始图片名称并设置回 populationDto
                RecomDto originalDto = recomService.findRecomByCode(recomDto.getRecomno());
                if (originalDto != null) {
                    recomDto.setRecomimage(originalDto.getRecomimage());
                }
            }

            // 更新数据到数据库
            int result = recomDao.updateRecom(recomDto);

            if (result > 0) {
                // 更新数据库中的浏览次数为 0
                recomDao.updateRecomViews(recomDto.getRecomno(), 0);

                modelAndView.setViewName("redirect:/recom/recomlist");
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

        // 将 populationDto 放入模型中
        modelAndView.addObject("recom", recomDto);

        return modelAndView;
    }

    @PostMapping("/recomdelete/{recomno}")
    public String deleteRecom(@PathVariable int recomno, Model model) {
        recomService.deleteRecom(recomno);
        return "redirect:/recom/recomlist";
    }




}
