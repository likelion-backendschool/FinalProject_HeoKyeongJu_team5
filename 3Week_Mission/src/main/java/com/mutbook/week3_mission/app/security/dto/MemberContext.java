package com.mutbook.week3_mission.app.security.dto;

import com.mutbook.week3_mission.app.domain.member.entity.AuthLevel;
import com.mutbook.week3_mission.app.domain.member.entity.Member;
import com.mutbook.week3_mission.app.domain.member.entity.Type;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;


@Getter
public class MemberContext extends User {
    private final Long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final String username;
    private final String nickname;
    private final String email;
    private AuthLevel authLevel;
    private final long cash;

    public MemberContext(Member member, List<GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);
        this.id = member.getId();
        this.createDate = member.getCreateDate();
        this.modifyDate = member.getModifyDate();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.authLevel = member.getAuthLevel();
        this.cash = member.getCash();
    }

    public Member getMember() {
        return Member
                .builder()
                .id(id)
                .createDate(createDate)
                .modifyDate(modifyDate)
                .username(username)
                .email(email)
                .authLevel(authLevel)
                .cash(cash)
                .nickname(nickname)
                .build();
    }

    public String getName() {
        return getUsername();
    }

    public boolean hasAuthority(String authorityName) {
        return getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authorityName));
    }
}