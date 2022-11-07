package com.mutbook.week4_mission.service;

import com.mutbook.week4_mission.app.domain.mail.service.EmailService;
import com.mutbook.week4_mission.app.domain.member.entity.Member;
import com.mutbook.week4_mission.app.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class EmailServiceTests {
    @Autowired
    EmailService emailService;
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("이메일 전송")
    void test1() {
        Member member = memberService.findByUsername("허경주").get();
        emailService.sendJoinMail(member);

    }
}
