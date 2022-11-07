package com.mutbook.week4_mission.app.domain.withdraw.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class WithdrawDto {
    @NotNull
    private String accountNumber;
    @NotNull
    private String bank;
    @NotNull
    private long withdrawAmount;
}
