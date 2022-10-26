package com.mutbook.week2_mission.app.domain.order.service;

import com.mutbook.week2_mission.app.attr.service.AttrService;
import com.mutbook.week2_mission.app.domain.cart.entity.CartItem;
import com.mutbook.week2_mission.app.domain.cart.service.CartService;
import com.mutbook.week2_mission.app.domain.member.entity.Member;
import com.mutbook.week2_mission.app.domain.member.service.MemberService;
import com.mutbook.week2_mission.app.domain.order.entity.Order;
import com.mutbook.week2_mission.app.domain.order.entity.OrderItem;
import com.mutbook.week2_mission.app.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .build();

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        orderRepository.save(order);

        return order;
    }

    public Optional<Order> findById(long id) {
        return orderRepository.findById(id);
    }

    public boolean actorCanSee(Member actor, Order order) {
        return actor.getId().equals(order.getBuyer().getId());
    }

    public List<Order> findAllByBuyerId(Long id){
        return orderRepository.findAllByBuyerId(id);
    }
}
