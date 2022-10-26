package com.mutbook.week2_mission.service;

import com.mutbook.week2_mission.app.domain.member.entity.Member;
import com.mutbook.week2_mission.app.domain.member.entity.AuthLevel;
import com.mutbook.week2_mission.app.domain.member.entity.Type;
import com.mutbook.week2_mission.app.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberServiceTests {
    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입")
    void test1() {
        String username = "testUser1";
        String password = "test1234";
        String email = "testUser1@test.com";
        String nickname = "닉넴1";

        memberService.join(username, password, email,nickname);

        Member foundMember = memberService.findByUsername("testUser1").get();

        assertThat(foundMember.getCreateDate()).isNotNull();
        assertThat(foundMember.getUsername()).isNotNull();
        assertThat(passwordEncoder.matches(password, foundMember.getPassword())).isTrue();
        assertThat(foundMember.getAuthLevel()).isEqualTo(3);
        assertThat(foundMember.getType()).isEqualTo(Type.USER);

    }
}
