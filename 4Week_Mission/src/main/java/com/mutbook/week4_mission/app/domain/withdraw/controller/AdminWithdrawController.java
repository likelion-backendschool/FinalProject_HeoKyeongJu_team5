package com.mutbook.week4_mission.app.domain.withdraw.controller;

import com.mutbook.week4_mission.app.domain.withdraw.entity.Withdraw;
import com.mutbook.week4_mission.app.domain.withdraw.service.WithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/withdraw")
public class AdminWithdrawController {
    private final WithdrawService withdrawService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/applyList")
    public String showList(Model model){
        List<Withdraw> items = withdrawService.findAll();
        model.addAttribute("items", items);
        return "/admin/withdraw/applyList";
    }
    // POST /adm/withdraw/{withdrawApplyId}
}
