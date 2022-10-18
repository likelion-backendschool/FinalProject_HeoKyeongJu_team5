package com.mutbook.week1_mission.app.domain.member.service;

import com.mutbook.week1_mission.app.domain.member.entity.AuthLevel;
import com.mutbook.week1_mission.app.domain.member.entity.Member;
import com.mutbook.week1_mission.app.domain.member.entity.Type;
import com.mutbook.week1_mission.app.domain.member.exception.AlreadyExistException;
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

        return member;
    }
    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public void login(String username, String password) {
        if(! memberRepository.findByUsername(username).isPresent()){
            // 존재하지 않는 회원임
        }
    }
}
