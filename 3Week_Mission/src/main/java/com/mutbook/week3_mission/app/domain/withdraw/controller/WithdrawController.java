package com.mutbook.week3_mission.app.domain.withdraw.controller;

import com.mutbook.week3_mission.app.base.rq.Rq;
import com.mutbook.week3_mission.app.domain.withdraw.dto.WithdrawDto;
import com.mutbook.week3_mission.app.domain.withdraw.entity.Withdraw;
import com.mutbook.week3_mission.app.domain.withdraw.service.WithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/withdraw")
public class WithdrawController {
    private final WithdrawService withdrawService;
    private final Rq rq;

    @GetMapping("")
    public String showList(Model model){
        List<Withdraw> items = withdrawService.findAllByMember(rq.getMember());
        model.addAttribute("items", items);
        return "/withdraw/main";
    }

    @GetMapping("/apply")
    public String showApplyForm(){
        return "/withdraw/apply";
    }
    @PostMapping("/apply")
    public String apply(@Valid WithdrawDto withdrawDto){
        withdrawService.apply(withdrawDto.getAccountNumber(), withdrawDto.getBank(), withdrawDto.getWithdrawAmount());
        return "redirect:/withdraw";
    }
}
