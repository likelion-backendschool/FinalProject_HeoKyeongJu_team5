package com.mutbook.week1_mission.app.domain.member.controller;

import com.mutbook.week1_mission.app.domain.member.dto.JoinDto;
import com.mutbook.week1_mission.app.domain.member.dto.LoginDto;
import com.mutbook.week1_mission.app.domain.member.service.MemberService;
import com.mutbook.week1_mission.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

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
}
