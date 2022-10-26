package com.mutbook.week2_mission.app.domain.order.entity;

import com.mutbook.week2_mission.app.base.entity.BaseEntity;
import com.mutbook.week2_mission.app.domain.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Table(name = "product_order")
public class Order extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Member buyer;
    private String name;
    @Enumerated(STRING)
    private ReadyStatus readyStatus; // 주문 상태 (준비, 결제, 취소)
    private LocalDateTime payDate; // 결제 날짜
    private boolean isPaid; // 결제 여부
    private boolean isCanceled; // 취소 여부
    private boolean isRefunded; // 환불 여부
    private Long payPrice; // 결제 예정 금액

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItem.setOrder(this);
        orderItems.add(orderItem);
    }
    public void makeName() {
        String name = orderItems.get(0).getProduct().getSubject();

        if (orderItems.size() > 1) {
            name += " 외 %d권".formatted(orderItems.size() - 1);
        }

        this.name = name;
    }
    public void calcPayPrice() {
        Long payPrice = 0L;
        for (OrderItem orderItem : orderItems) {
            payPrice += orderItem.getPayPrice();
        }

        this.payPrice = payPrice;
    }
}