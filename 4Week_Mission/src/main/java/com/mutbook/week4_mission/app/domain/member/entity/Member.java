package com.mutbook.week4_mission.app.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mutbook.week4_mission.app.base.entity.BaseEntity;
import com.mutbook.week4_mission.util.Util;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "member")
@ToString(callSuper = true, exclude = "password")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @Convert(converter = AuthLevel.Converter.class)
    @Column(name = "auth_level")
    private AuthLevel authLevel;
    @Column(name ="cash")
    private long cash;
    @Column(columnDefinition = "TEXT")
    private String accessToken;

    public List<GrantedAuthority> genAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("MEMBER"));

        // 닉네임을 가지고 있다면 작가의 권한을 가진다.
        if (StringUtils.hasText(nickname)) {
            authorities.add(new SimpleGrantedAuthority("AUTHOR"));
        }

        return authorities;
    }

    public static Member fromMap(Map<String, Object> map) {
        return fromJwtClaims(map);
    }

    public static Member fromJwtClaims(Map<String, Object> jwtClaims) {
        long id = 0;

        if (jwtClaims.get("id") instanceof Long) {
            id = (long) jwtClaims.get("id");
        } else if (jwtClaims.get("id") instanceof Integer) {
            id = (long) (int) jwtClaims.get("id");
        }

        LocalDateTime createDate = null;
        LocalDateTime modifyDate = null;

        if (jwtClaims.get("createDate") instanceof List) {
            createDate = Util.date.bitsToLocalDateTime((List<Integer>) jwtClaims.get("createDate"));
        }

        if (jwtClaims.get("modifyDate") instanceof List) {
            modifyDate = Util.date.bitsToLocalDateTime((List<Integer>) jwtClaims.get("modifyDate"));
        }

        String username = (String) jwtClaims.get("username");
        String email = (String) jwtClaims.get("email");
        String accessToken = (String) jwtClaims.get("accessToken");

        return Member
                .builder()
                .id(id)
                .createDate(createDate)
                .modifyDate(modifyDate)
                .username(username)
                .email(email)
                .accessToken(accessToken)
                .build();
    }

    public Map<String, Object> getAccessTokenClaims() {
        return Util.mapOf(
                "id", getId(),
                "createDate", getCreateDate(),
                "modifyDate", getModifyDate(),
                "username", getUsername(),
                "email", getEmail(),
                "authorities", genAuthorities()
        );
    }
}
