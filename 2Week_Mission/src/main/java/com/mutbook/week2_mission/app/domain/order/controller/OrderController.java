package com.mutbook.week2_mission.app.domain.order.controller;

import com.mutbook.week2_mission.app.base.rq.Rq;
import com.mutbook.week2_mission.app.domain.member.entity.Member;
import com.mutbook.week2_mission.app.domain.member.service.MemberService;
import com.mutbook.week2_mission.app.domain.order.entity.Order;
import com.mutbook.week2_mission.app.domain.order.exception.ActorCanNotSeeOrderException;
import com.mutbook.week2_mission.app.domain.order.service.OrderService;
import com.mutbook.week2_mission.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final Rq rq;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String makeOrder(@AuthenticationPrincipal MemberContext memberContext) {
        Member member = memberContext.getMember();
        Order order = orderService.createFromCart(member);

        return "redirect:/order/list";
    }
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showOrderDetail(@AuthenticationPrincipal MemberContext memberContext, @PathVariable Long id, Model model){
        Order order = orderService.findById(id).get();

        Member actor = memberContext.getMember();

        Long restCash = memberService.getCash(actor);

        if (orderService.actorCanSee(actor, order) == false) {
            throw new ActorCanNotSeeOrderException();
        }

        model.addAttribute("order", order);
        model.addAttribute("actorCash", restCash);

        return "/order/detail";
    }
    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String showOrderList(Model model){
        List<Order> orders = orderService.findAllByBuyerId(rq.getId());
        model.addAttribute("orders", orders);

        return "/order/list";
    }
}
