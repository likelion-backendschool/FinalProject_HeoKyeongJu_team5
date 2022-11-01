package com.mutbook.week3_mission.app.domain.rebate.controller;

import com.mutbook.week3_mission.app.domain.rebate.service.RebateService;
import com.mutbook.week3_mission.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/rebate")
public class AdminRebateController {
    private final RebateService rebateService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String showRebateList(){
        return "admin/rebate/list";
    }
    @PostMapping("/makeData")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public void makeData(String yearMonth) {
        rebateService.makeDate(yearMonth);
    }
}
