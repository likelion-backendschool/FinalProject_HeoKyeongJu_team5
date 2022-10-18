package com.mutbook.week1_mission.app.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mutbook.week1_mission.app.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@Table(name = "member")
@ToString(callSuper = true, exclude = "password")
public class Member extends BaseEntity {
    @Column(unique = true, name = "username")
    private String username;
    @Column(name = "password")
    @JsonIgnore
    private String password;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "email")
    private String email;
    @Column(name = "auth_level")
    private int authLevel;
    @Enumerated(STRING)
    @Column(name = "type")
    private Type type;
}
