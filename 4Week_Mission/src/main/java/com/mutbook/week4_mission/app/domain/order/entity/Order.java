package com.mutbook.week4_mission.app.domain.order.entity;

import com.mutbook.week4_mission.app.base.entity.BaseEntity;
import com.mutbook.week4_mission.app.domain.member.entity.Member;
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
    private OrderStatus orderStatus; // 주문 상태 (준비, 결제, 취소)
    private LocalDateTime refundDate;
    private LocalDateTime payDate;
    private LocalDateTime cancelDate;
    private int payPrice; // 결제 예정 금액

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
    public int calcPayPrice() {
        int payPrice = 0;
        for (OrderItem orderItem : orderItems) {
            payPrice += orderItem.getPayPrice();
        }

        this.payPrice = payPrice;
        return payPrice;
    }
    public void setPaymentDone() {
        payDate = LocalDateTime.now();

        for (OrderItem orderItem : orderItems) {
            orderItem.setPaymentDone();
        }
        orderStatus = OrderStatus.PAIED_STATUS;
    }
    public void setCancelDone() {
        cancelDate = LocalDateTime.now();

        orderStatus = OrderStatus.CANCELED_STATUS;
    }
    public void setRefundDone() {
        refundDate = LocalDateTime.now();

        for (OrderItem orderItem : orderItems) {
            orderItem.setRefundDone();
        }

        orderStatus = OrderStatus.REFUNDED_STATUS;
    }
    public boolean isPayable() {
        if ( orderStatus.equals(OrderStatus.PAIED_STATUS) ) return false;
        if ( orderStatus.equals(OrderStatus.CANCELED_STATUS) ) return false;
        return true;
    }
}