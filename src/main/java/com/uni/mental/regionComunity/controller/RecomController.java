package com.uni.mental.regionComunity.controller;


import com.uni.mental.regionComunity.model.dao.RecomDao;
import com.uni.mental.regionComunity.model.service.RecomService;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String recomInsertPage() {return "recom/recomenroll";}


}
