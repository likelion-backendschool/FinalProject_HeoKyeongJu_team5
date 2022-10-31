package com.mutbook.week2_mission.app.domain.postTag.entity;

import com.mutbook.week2_mission.app.base.entity.BaseEntity;
import com.mutbook.week2_mission.app.domain.member.entity.Member;
import com.mutbook.week2_mission.app.domain.post.entity.Post;
import com.mutbook.week2_mission.app.domain.postkeyword.entity.PostKeyword;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class PostTag extends BaseEntity {
    @ManyToOne
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    @ToString.Exclude
    private Member member;

    @ManyToOne
    @ToString.Exclude
    private PostKeyword postKeyword;
}
