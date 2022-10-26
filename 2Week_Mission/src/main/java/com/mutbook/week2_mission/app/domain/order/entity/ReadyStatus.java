package com.mutbook.week2_mission.app.domain.order.entity;

import java.util.stream.Stream;

public enum ReadyStatus {
    READY_STATUS("결제 대기중"), PAIED_STATUS("결제 완료"), CANCELED_STATUS("주문 취소");
    private String typeOfOrder;

        ReadyStatus(String typeOfOrder) {
        this.typeOfOrder = typeOfOrder;
        }

// standard getters and setters

public static Stream<ReadyStatus> stream() {
        return Stream.of(ReadyStatus.values());
        }
}
