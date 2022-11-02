package com.mutbook.week3_mission.app.domain.withdraw.entity;

import com.mutbook.week3_mission.app.base.entity.BaseEntity;
import com.mutbook.week3_mission.app.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Table(name = "withdraw")
public class Withdraw extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bank")
    private String bank;

    @Column(name = "withdraw_amount")
    private long withdrawAmount;
}
