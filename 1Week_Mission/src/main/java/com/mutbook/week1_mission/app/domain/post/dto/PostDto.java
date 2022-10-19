package com.mutbook.week1_mission.app.domain.post.dto;

import com.mutbook.week1_mission.app.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PostDto {
    @NotNull
    private String subject;
    @NotNull
    private String content;
    @NotNull
    private String contentHtml;
    @NotNull
    private Member author;
}
