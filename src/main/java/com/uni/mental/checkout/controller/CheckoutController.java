package com.uni.mental.checkout.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("checkup")
public class CheckoutController {

    @GetMapping(value={"/checkup"})
    public String checkup(){
        return "checkup/checkup";
    }
    @GetMapping(value={"/checkupDetail"})
    public String checkupDetail(){
        return "checkup/checkupDetail";
    }
}
