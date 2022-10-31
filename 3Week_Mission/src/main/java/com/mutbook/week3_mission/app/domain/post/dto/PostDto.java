package com.mutbook.week3_mission.app.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
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
    @NotBlank
    private String postTagContents;
}
