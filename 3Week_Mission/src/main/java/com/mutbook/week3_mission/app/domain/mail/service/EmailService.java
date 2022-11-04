package com.mutbook.week3_mission.app.domain.mail.service;

import com.mutbook.week3_mission.app.domain.member.entity.Member;
import com.mutbook.week3_mission.app.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailService {
    private JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "beeewt@gmail.com";
    private final MemberRepository memberRepository;


    public void sendJoinMail(Member member) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_ADDRESS);
        message.setTo(member.getEmail());
        message.setSubject("[ MutBooks ] 회원가입을 환영합니다.");
        message.setText("MutBooks에 가입한 "+ member.getUsername()+"님 환영합니다.");
        javaMailSender.send(message);
    }
}
