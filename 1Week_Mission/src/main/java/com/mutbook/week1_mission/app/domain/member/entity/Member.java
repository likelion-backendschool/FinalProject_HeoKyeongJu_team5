package com.mutbook.week1_mission.app.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mutbook.week1_mission.app.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
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
    @Column(unique = true, name = "email")
    private String email;
    @Column(name = "auth_level")
    private int authLevel;
    @Enumerated(STRING)
    @Column(name = "type")
    private Type type;

    public List<GrantedAuthority> genAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("MEMBER"));

        // 닉네임을 가지고 있다면 작가의 권한을 가진다.
        if (StringUtils.hasText(nickname)) {
            authorities.add(new SimpleGrantedAuthority("AUTHOR"));
        }

        return authorities;
    }
}
