package com.mutbook.week1_mission.app.base.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @LastModifiedDate
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @Transient
    @Builder.Default
    private Map<String, Object> extra = new LinkedHashMap<>();

    public BaseEntity(long id) {
        this.id = id;
    }

    public BaseEntity(LocalDateTime createDate, LocalDateTime modifyDate) {
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}