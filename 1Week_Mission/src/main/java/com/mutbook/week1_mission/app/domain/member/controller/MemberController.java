package com.mutbook.week1_mission.app.domain.member.controller;

import com.mutbook.week1_mission.app.base.dto.RsData;
import com.mutbook.week1_mission.app.base.rq.Rq;
import com.mutbook.week1_mission.app.domain.member.dto.JoinDto;
import com.mutbook.week1_mission.app.domain.member.entity.Member;
import com.mutbook.week1_mission.app.domain.member.service.MemberService;
import com.mutbook.week1_mission.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String showMemberInfo(){
        return "member/profile";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinDto joinDto) {
        memberService.join(joinDto.getUsername(), joinDto.getPassword(), joinDto.getEmail());

        return "redirect:/member/login?msg=" + Util.url.encode("회원가입이 완료되었습니다.");
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin() {
        return "member/login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/findUsername")
    public String showFindUser(){
        return "member/findUsername";
    }
    @PreAuthorize("isAnonymous()")
    @PostMapping("/findUsername")
    public String findUsername(String email, Model model) {
        Member member = memberService.findByEmail(email).orElse(null);
        if (member == null) {
            return rq.historyBack("일치하는 회원이 존재하지 않습니다.");
        }
        return Rq.redirectWithMsg("/member/login?username=%s&".formatted(member.getUsername()), "해당 이메일로 가입한 계정의 아이디는 '%s' 입니다.".formatted(member.getUsername()));
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/beAuthor")
    public String showBeAuthor() {
        return "member/beAuthor";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/beAuthor")
    public String beAuthor(String nickname) {
        Member member = rq.getMember();

        RsData rsData = memberService.beAuthor(member, nickname);

        if (rsData.isFail()) {
            return Rq.redirectWithMsg("/member/beAuthor", rsData);
        }

        return Rq.redirectWithMsg("/", rsData);
    }
}
