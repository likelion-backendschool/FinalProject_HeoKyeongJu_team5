package com.mutbook.week1_mission.app.domain.productTag.entity;


import com.mutbook.week1_mission.app.base.entity.BaseEntity;
import com.mutbook.week1_mission.app.domain.member.entity.Member;
import com.mutbook.week1_mission.app.domain.product.entity.Product;
import com.mutbook.week1_mission.app.domain.productKeyword.entity.ProductKeyword;
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
public class ProductTag extends BaseEntity {
    @ManyToOne
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne
    @ToString.Exclude
    private Member member;

    @ManyToOne
    @ToString.Exclude
    private ProductKeyword productKeyword;
}
