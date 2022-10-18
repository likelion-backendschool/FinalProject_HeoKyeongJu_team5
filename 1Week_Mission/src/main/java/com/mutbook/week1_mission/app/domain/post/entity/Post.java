package com.mutbook.week1_mission.app.domain.post.entity;

import com.mutbook.week1_mission.app.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@Table(name = "post")
public class Post extends BaseEntity {
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "subject")
    private String subject;
    @Column(name = "content")
    private String content;
    @Column(name = "content_html")
    private String contentHtml;

}
