package com.mutbook.week3_mission.app.domain.mail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class JoinEmailDto {
    @NotNull
    private String address;
    @NotNull
    private String subject;
    @NotNull
    private String body;
}