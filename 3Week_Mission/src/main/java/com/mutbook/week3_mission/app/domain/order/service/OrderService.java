package com.mutbook.week3_mission.app.domain.order.service;

import com.mutbook.week3_mission.app.base.dto.RsData;
import com.mutbook.week3_mission.app.domain.cart.entity.CartItem;
import com.mutbook.week3_mission.app.domain.cart.service.CartService;
import com.mutbook.week3_mission.app.domain.member.entity.Member;
import com.mutbook.week3_mission.app.domain.member.service.MemberService;
import com.mutbook.week3_mission.app.domain.myBook.service.MyBookService;
import com.mutbook.week3_mission.app.domain.order.entity.Order;
import com.mutbook.week3_mission.app.domain.order.entity.OrderItem;
import com.mutbook.week3_mission.app.domain.order.entity.OrderStatus;
import com.mutbook.week3_mission.app.domain.order.repository.OrderItemRepository;
import com.mutbook.week3_mission.app.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final MemberService memberService;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final MyBookService myBookService;
    private final OrderItemRepository orderItemRepository;


    @Transactional
    public Order createFromCart(Member member) {
        List<CartItem> cartItems = cartService.getItemsByMember(member);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            orderItems.add(new OrderItem(cartItem.getProduct()));
            cartService.removeItem(cartItem);
        }
        return create(member, orderItems);
    }

    @Transactional
    public Order create(Member member, List<OrderItem> orderItems) {

        Order order = Order
                .builder()
                .buyer(member)
                .orderStatus(OrderStatus.READY_STATUS)
                .build();

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.makeName();
        order.calcPayPrice();

        orderRepository.save(order);

        return order;
    }

    @Transactional
    public RsData payByTossPayments(Order order, long useRestCash) {
        Member buyer = order.getBuyer();
        long restCash = buyer.getCash();
        int payPrice = order.calcPayPrice();

        long pgPayPrice = payPrice - useRestCash;
        memberService.addCash(buyer, pgPayPrice, "??????__%d__??????__??????????????????".formatted(order.getId()));
        memberService.addCash(buyer, pgPayPrice * -1, "??????__%d__??????__??????????????????".formatted(order.getId()));

        if (useRestCash > 0) {
            if ( useRestCash > restCash ) {
                throw new RuntimeException("???????????? ???????????????.");
            }

            memberService.addCash(buyer, useRestCash * -1, "??????__%d__??????__?????????".formatted(order.getId()));
        }

        payDone(order);

        return RsData.of("S-1", "????????? ?????????????????????.");
    }

    @Transactional
    public RsData payByRestCashOnly(Order order) {
        Member buyer = order.getBuyer();
        long restCash = buyer.getCash();
        long payPrice = order.calcPayPrice();

        if (payPrice > restCash) {
            throw new RuntimeException("???????????? ???????????????.");
        }

        memberService.addCash(buyer, payPrice * -1, "??????__%d__??????__?????????".formatted(order.getId()));

        payDone(order);

        return RsData.of("S-1", "????????? ?????????????????????.");
    }

    private void payDone(Order order) {
        order.setPaymentDone();
        myBookService.add(order);
        orderRepository.save(order);
    }

    @Transactional
    public RsData refund(Order order, Member actor) {
        RsData actorCanRefundRsData = actorCanRefund(actor, order);

        if (actorCanRefundRsData.isFail()) {
            return actorCanRefundRsData;
        }

        order.setCancelDone();

        long payPrice = order.getPayPrice();
        memberService.addCash(order.getBuyer(), payPrice, "??????__%d__??????__?????????".formatted(order.getId()));

        order.setRefundDone();
        orderRepository.save(order);

        myBookService.remove(order);

        return RsData.of("S-1", "?????????????????????.");
    }

    @Transactional
    public RsData refund(Long orderId, Member actor) {
        Order order = findById(orderId).orElse(null);

        if (order == null) {
            return RsData.of("F-2", "?????? ????????? ?????? ??? ????????????.");
        }
        return refund(order, actor);
    }

    public RsData actorCanRefund(Member actor, Order order) {

        if (order.getOrderStatus().equals(OrderStatus.CANCELED_STATUS)) {
            return RsData.of("F-1", "?????? ?????????????????????.");
        }

        if ( order.getOrderStatus().equals(OrderStatus.REFUNDED_STATUS)) {
            return RsData.of("F-4", "?????? ?????????????????????.");
        }

        if ( order.getOrderStatus().equals(OrderStatus.READY_STATUS) ) {
            return RsData.of("F-5", "????????? ????????? ????????? ???????????????.");
        }

        if (actor.getId().equals(order.getBuyer().getId()) == false) {
            return RsData.of("F-2", "????????? ????????????.");
        }

        long between = ChronoUnit.MINUTES.between(order.getPayDate(), LocalDateTime.now());

        if (between > 10) {
            return RsData.of("F-3", "?????? ?????? 10?????? ???????????????, ?????? ??? ??? ????????????.");
        }

        return RsData.of("S-1", "????????? ??? ????????????.");
    }

    public Optional<Order> findForPrintById(long id) {
        return findById(id);
    }

    public Optional<Order> findById(long id) {
        return orderRepository.findById(id);
    }

    public boolean actorCanSee(Member actor, Order order) {
        return actor.getId().equals(order.getBuyer().getId());
    }

    public boolean actorCanPayment(Member actor, Order order) {
        return actorCanSee(actor, order);
    }


    public List<Order> findAllByBuyerId(Long memberId) {
        return orderRepository.findAllByBuyerIdOrderByIdDesc(memberId);
    }

    @Transactional
    public RsData cancel(Order order, Member actor) {
        RsData actorCanCancelRsData = actorCanCancel(actor, order);

        if (actorCanCancelRsData.isFail()) {
            return actorCanCancelRsData;
        }

        order.setOrderStatus(OrderStatus.CANCELED_STATUS);

        return RsData.of("S-1", "?????????????????????.");
    }

    @Transactional
    public RsData cancel(Long orderId, Member actor) {
        Order order = findById(orderId).get();
        return cancel(order, actor);
    }

    public RsData actorCanCancel(Member actor, Order order) {
        if ( order.getOrderStatus().equals(OrderStatus.PAIED_STATUS)) {
            return RsData.of("F-3", "?????? ???????????? ???????????????.");
        }

        if (order.getOrderStatus().equals(OrderStatus.CANCELED_STATUS)) {
            return RsData.of("F-1", "?????? ?????????????????????.");
        }

        if (actor.getId().equals(order.getBuyer().getId()) == false) {
            return RsData.of("F-2", "????????? ????????????.");
        }

        return RsData.of("S-1", "????????? ??? ????????????.");
    }
    public List<OrderItem> findAllByPayDateBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemRepository.findAllByPayDateBetween(fromDate, toDate);
    }

    public List<OrderItem> findAllByPayDateBetweenOrderByIdAsc(LocalDateTime fromDate, LocalDateTime toDate) {
        return orderItemRepository.findAllByPayDateBetween(fromDate, toDate);
    }
}
