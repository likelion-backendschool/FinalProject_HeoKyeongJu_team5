package com.mutbook.week1_mission.app.domain.member.service;

import com.mutbook.week1_mission.app.domain.mail.service.EmailService;
import com.mutbook.week1_mission.app.domain.member.entity.AuthLevel;
import com.mutbook.week1_mission.app.domain.member.entity.Member;
import com.mutbook.week1_mission.app.domain.member.entity.Type;
import com.mutbook.week1_mission.app.domain.member.exception.AlreadyExistException;
import com.mutbook.week1_mission.app.domain.member.exception.NotExistUserException;
import com.mutbook.week1_mission.app.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public Optional<Member> findByEmail(String email){
        if(memberRepository.findByEmail(email).isPresent()){
            return memberRepository.findByEmail(email);
        } else {
            throw new NotExistUserException();
        }
    }

    public Member join(String username, String password, String email){
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new AlreadyExistException();
        }


        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .authLevel(AuthLevel.ROLE_DEFAULT)
                .type(Type.USER)
                .build();

        memberRepository.save(member);
        //emailService.sendJoinMail(member);
        return member;
    }



    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }


}
