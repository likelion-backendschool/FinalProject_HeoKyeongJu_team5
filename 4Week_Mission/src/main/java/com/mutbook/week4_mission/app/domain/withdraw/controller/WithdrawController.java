package com.mutbook.week4_mission.app.domain.withdraw.controller;

import com.mutbook.week4_mission.app.base.rq.Rq;
import com.mutbook.week4_mission.app.domain.member.entity.Member;
import com.mutbook.week4_mission.app.domain.withdraw.dto.WithdrawDto;
import com.mutbook.week4_mission.app.domain.withdraw.entity.Withdraw;
import com.mutbook.week4_mission.app.domain.withdraw.service.WithdrawService;
import com.mutbook.week4_mission.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("isAuthenticated()")
    public String showList(Model model){
        List<Withdraw> items = withdrawService.findAllByMember(rq.getMember());
        model.addAttribute("items", items);
        return "/withdraw/main";
    }

    @GetMapping("/apply")
    @PreAuthorize("isAuthenticated()")
    public String showApplyForm(){
        return "/withdraw/apply";
    }
    @PostMapping("/apply")
    @PreAuthorize("isAuthenticated()")
    public String apply(@AuthenticationPrincipal MemberContext memberContext, @Valid WithdrawDto withdrawDto){
        Member member = memberContext.getMember();
        withdrawService.apply(member, withdrawDto.getAccountNumber(), withdrawDto.getBank(), withdrawDto.getWithdrawAmount());
        return "redirect:/withdraw";
    }
}
