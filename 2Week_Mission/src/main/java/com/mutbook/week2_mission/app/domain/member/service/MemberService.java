package com.mutbook.week2_mission.app.domain.member.service;

import com.mutbook.week2_mission.app.base.dto.RsData;
import com.mutbook.week2_mission.app.domain.mail.service.EmailService;
import com.mutbook.week2_mission.app.domain.member.entity.AuthLevel;
import com.mutbook.week2_mission.app.domain.member.entity.Member;
import com.mutbook.week2_mission.app.domain.member.entity.Type;
import com.mutbook.week2_mission.app.base.exception.AlreadyExistException;
import com.mutbook.week2_mission.app.domain.member.repository.MemberRepository;
import com.mutbook.week2_mission.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
            return memberRepository.findByEmail(email);
    }

    public Member join(String username, String password, String email, String nickname){
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new AlreadyExistException();
        }


        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .nickname(nickname)
                .authLevel(AuthLevel.ROLE_DEFAULT)
                .build();

        member.genAuthorities();
        memberRepository.save(member);
        //emailService.sendJoinMail(member);
        return member;
    }



    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }


    @Transactional
    public RsData beAuthor(Member member, String nickname) {
        Optional<Member> opMember = memberRepository.findByNickname(nickname);

        if (opMember.isPresent()) {
            return RsData.of("F-1", "해당 필명은 이미 사용중입니다.");
        }

        opMember = memberRepository.findById(member.getId());

        opMember.get().setNickname(nickname);
        forceAuthentication(opMember.get());

        return RsData.of("S-1", "해당 필명으로 활동을 시작합니다.");
    }
    private void forceAuthentication(Member member) {
        MemberContext memberContext = new MemberContext(member, member.genAuthorities());

        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        memberContext,
                        member.getPassword(),
                        memberContext.getAuthorities()
                );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    public Long getCash(Member member) {
        Member foundMember = findByUsername(member.getUsername()).get();
        return foundMember.getCash();
    }
}
