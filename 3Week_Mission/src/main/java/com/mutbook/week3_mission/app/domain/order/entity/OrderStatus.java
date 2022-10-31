package com.mutbook.week3_mission.app.domain.order.entity;

import java.util.stream.Stream;

public enum OrderStatus {
    READY_STATUS("결제 대기중"), PAIED_STATUS("결제 완료"), CANCELED_STATUS("주문 취소"), REFUNDED_STATUS("환불");
    private String typeOfOrder;

        OrderStatus(String typeOfOrder) {
        this.typeOfOrder = typeOfOrder;
        }

// standard getters and setters

public static Stream<OrderStatus> stream() {
        return Stream.of(OrderStatus.values());
        }
}
