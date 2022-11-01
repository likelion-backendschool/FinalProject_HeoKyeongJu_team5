package com.mutbook.week3_mission.app.domain.withdraw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/withdraw")
public class WithdrawController {
    @GetMapping("")
    public String showWithdrawForm(){
        return "/withdraw/main";
    }

}
