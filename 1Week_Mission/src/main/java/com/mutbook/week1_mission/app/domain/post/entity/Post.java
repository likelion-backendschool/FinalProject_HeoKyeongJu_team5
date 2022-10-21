package com.mutbook.week1_mission.app.domain.post.entity;

import com.mutbook.week1_mission.app.base.entity.BaseEntity;
import com.mutbook.week1_mission.app.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@SuperBuilder
@Table(name = "post")
public class Post extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Member author;
//    @Column(name = "author_id")
//    private Long authorId;
    @Column(name = "subject")
    private String subject;
    @Column(name = "content")
    private String content;
    @Column(name = "content_html")
    private String contentHtml;
//    @ManyToMany
//    @Column(name = "post_hash_tag")
//    private Set<HashTag> hashTag;
}
