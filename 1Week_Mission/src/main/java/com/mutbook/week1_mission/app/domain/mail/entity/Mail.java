package com.mutbook.week1_mission.app.domain.mail.entity;

import com.mutbook.week1_mission.app.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Table(name = "Mail")
public class Mail extends BaseEntity {
    @Column(name = "address")
    private String address;
    @Column(name = "subject")
    private String subject;
    @Column(name = "body")
    private String body;
    @Column(name = "send_end_date")
    private LocalDateTime sendEndDate;
}
