package com.mutbook.week3_mission.app.domain.order.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mutbook.week3_mission.app.base.dto.RsData;
import com.mutbook.week3_mission.app.base.rq.Rq;
import com.mutbook.week3_mission.app.domain.member.entity.Member;
import com.mutbook.week3_mission.app.domain.member.service.MemberService;
import com.mutbook.week3_mission.app.domain.order.entity.Order;
import com.mutbook.week3_mission.app.domain.order.exception.ActorCanNotPayOrderException;
import com.mutbook.week3_mission.app.domain.order.exception.ActorCanNotSeeOrderException;
import com.mutbook.week3_mission.app.domain.order.exception.OrderIdNotMatchedException;
import com.mutbook.week3_mission.app.domain.order.exception.OrderNotEnoughCashException;
import com.mutbook.week3_mission.app.domain.order.service.OrderService;
import com.mutbook.week3_mission.app.security.dto.MemberContext;
import com.mutbook.week3_mission.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final Rq rq;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;



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

        return "order/detail";
    }
    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String showOrderList(Model model){
        List<Order> orders = orderService.findAllByBuyerId(rq.getId());
        model.addAttribute("orders", orders);

        return "/order/list";
    }
    @PostMapping("/{id}/payByRestCashOnly")
    @PreAuthorize("isAuthenticated()")
    public String payByRestCashOnly(@AuthenticationPrincipal MemberContext memberContext, @PathVariable long id) {
        Order order = orderService.findForPrintById(id).get();

        Member actor = memberContext.getMember();

        long restCash = memberService.getCash(actor);

        if (orderService.actorCanPayment(actor, order) == false) {
            throw new ActorCanNotPayOrderException();
        }

        RsData rsData = orderService.payByRestCashOnly(order);

        return "redirect:/order/%d?msg=%s".formatted(order.getId(), Util.url.encode("예치금으로 결제했습니다."));
    }
    @PostConstruct
    private void init() {
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) {
            }
        });
    }

    @Value("test_sk_jZ61JOxRQVEXZmQ1WnQ3W0X9bAqw")
    private String SECRET_KEY;

    @RequestMapping("/{id}/success")
    public String confirmPayment(
            @PathVariable long id,
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Long amount,
            Model model,
            @AuthenticationPrincipal MemberContext memberContext
    ) throws Exception {

        Order order = orderService.findForPrintById(id).get();

        long orderIdInputed = Long.parseLong(orderId.split("__")[1]);

        if (id != orderIdInputed) {
            throw new OrderIdNotMatchedException();
        }

        HttpHeaders headers = new HttpHeaders();
        // headers.setBasicAuth(SECRET_KEY, ""); // spring framework 5.2 이상 버전에서 지원
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payloadMap = new HashMap<>();
        payloadMap.put("orderId", orderId);
        payloadMap.put("amount", String.valueOf(amount));

        Member actor = memberContext.getMember();
        long restCash = memberService.getCash(actor);
        long payPriceRestCash = order.calcPayPrice() - amount;

        if (payPriceRestCash > restCash) {
            throw new OrderNotEnoughCashException();
        }

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://api.tosspayments.com/v1/payments/" + paymentKey, request, JsonNode.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            orderService.payByTossPayments(order, payPriceRestCash);

            return Rq.redirectWithMsg(
                    "/order/%d".formatted(order.getId()),
                    "%d번 주문이 결제처리되었습니다.".formatted(order.getId())
            );
        } else {
            JsonNode failNode = responseEntity.getBody();
            model.addAttribute("message", failNode.get("message").asText());
            model.addAttribute("code", failNode.get("code").asText());
            return "order/fail";
        }
    }

    @RequestMapping("/{id}/fail")
    public String failPayment(@RequestParam String message, @RequestParam String code, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("code", code);
        return "order/fail";
    }

    @PostMapping("/{orderId}/cancel")
    @PreAuthorize("isAuthenticated()")
    public String cancel(@PathVariable Long orderId) {
        RsData rsData = orderService.cancel(orderId, rq.getMember());

        if (rsData.isFail()) {
            return Rq.redirectWithErrorMsg("/order/%d".formatted(orderId), rsData);
        }

        return Rq.redirectWithMsg("/order/%d".formatted(orderId), rsData);
    }

    @PostMapping("/{orderId}/refund")
    @PreAuthorize("isAuthenticated()")
    public String refund(@PathVariable Long orderId) {
        RsData rsData = orderService.refund(orderId, rq.getMember());

        if (rsData.isFail()) {
            return Rq.redirectWithErrorMsg("/order/%d".formatted(orderId), rsData);
        }

        return Rq.redirectWithMsg("/order/%d".formatted(orderId), rsData);
    }
}
