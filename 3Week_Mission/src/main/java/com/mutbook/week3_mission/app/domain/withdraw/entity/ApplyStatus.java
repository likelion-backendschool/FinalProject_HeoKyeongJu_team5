package com.mutbook.week3_mission.app.domain.withdraw.entity;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum ApplyStatus {
    READY_STATUS("처리중"),COMPLETE_STATUS("출금 완료");
    private String stateOfApply;

        ApplyStatus(String stateOfApply) {
        this.stateOfApply = stateOfApply;
        }

// standard getters and setters

public static Stream<ApplyStatus> stream() {
        return Stream.of(ApplyStatus.values());
        }
}
