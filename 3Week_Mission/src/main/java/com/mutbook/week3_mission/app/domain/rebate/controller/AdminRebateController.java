package com.mutbook.week3_mission.app.domain.rebate.controller;

import com.mutbook.week3_mission.app.base.dto.RsData;
import com.mutbook.week3_mission.app.domain.rebate.entity.RebateOrderItem;
import com.mutbook.week3_mission.app.domain.rebate.service.RebateService;
import com.mutbook.week3_mission.util.Util;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/rebate")
@Slf4j
public class AdminRebateController {
    private final RebateService rebateService;

    @GetMapping("/makeData")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String showRebateList(){
        return "admin/rebate/makeData";
    }
    @PostMapping("/makeData")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public String makeData(String yearMonth) {
        RsData makeDateRsData = rebateService.makeDate(yearMonth);
        String redirect = makeDateRsData.addMsgToUrl("redirect:/admin/rebate/rebateOrderItemList?yearMonth=" + yearMonth);

        return redirect;
    }
    @GetMapping("/rebateOrderItemList")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showRebateOrderItemList(String yearMonth, Model model) {
        if (yearMonth == null) {
            yearMonth = "2022-10";
        }

        List<RebateOrderItem> items = rebateService.findRebateOrderItemsByPayDateIn(yearMonth);

        model.addAttribute("items", items);

        return "admin/rebate/rebateOrderItemList";
    }

    @PostMapping("/rebateOne/{orderItemId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String rebateOne(@PathVariable long orderItemId, HttpServletRequest req) {
        RsData rebateRsData = rebateService.rebate(orderItemId);

        String referer = req.getHeader("Referer");
        String yearMonth = Util.url.getQueryParamValue(referer, "yearMonth", "");

        String redirect = "redirect:/admin/rebate/rebateOrderItemList?yearMonth=" + yearMonth;

        redirect = rebateRsData.addMsgToUrl(redirect);

        return redirect;
    }

    @PostMapping("/rebate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String rebate(String ids, HttpServletRequest req) {

        String[] idsArr = ids.split(",");

        Arrays.stream(idsArr)
                .mapToLong(Long::parseLong)
                .forEach(id -> {
                    rebateService.rebate(id);
                });

        String referer = req.getHeader("Referer");
        String yearMonth = Util.url.getQueryParamValue(referer, "yearMonth", "");

        String redirect = "redirect:/admin/rebate/rebateOrderItemList?yearMonth=" + yearMonth;
        redirect += "&msg=" + Util.url.encode("%d건의 정산품목을 정산처리하였습니다.".formatted(idsArr.length));

        return redirect;
    }
}
