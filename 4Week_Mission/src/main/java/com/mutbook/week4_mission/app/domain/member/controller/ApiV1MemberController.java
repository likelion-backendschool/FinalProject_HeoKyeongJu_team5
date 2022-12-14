package com.mutbook.week4_mission.app.domain.member.controller;

import com.mutbook.week4_mission.app.base.dto.RsData;
import com.mutbook.week4_mission.app.base.rq.Rq;
import com.mutbook.week4_mission.app.domain.member.MemberResponse;
import com.mutbook.week4_mission.app.domain.member.dto.LoginDto;
import com.mutbook.week4_mission.app.domain.member.entity.Member;
import com.mutbook.week4_mission.app.domain.member.service.MemberService;
import com.mutbook.week4_mission.app.domain.myBook.MyBooksResponse;
import com.mutbook.week4_mission.app.security.dto.MemberContext;
import com.mutbook.week4_mission.util.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.ALL_VALUE;

@RestController
@RequestMapping("api/v1/member")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "ApiV1MemberController", description = "로그인 기능과 로그인 된 회원의 정보를 제공 기능을 담당하는 컨트롤러")
public class ApiV1MemberController {

    private final ModelMapper mapper;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final Rq rq;

    @PostMapping("/login")
    public ResponseEntity<RsData> login(@Valid @RequestBody LoginDto loginDto) {
        Member member = memberService.findByUsername(loginDto.getUsername()).orElse(null);

        if (member == null) {
            return Util.spring.responseEntityOf(RsData.of("F-2", "일치하는 회원이 존재하지 않습니다."));
        }

        if (passwordEncoder.matches(loginDto.getPassword(), member.getPassword()) == false) {
            return Util.spring.responseEntityOf(RsData.of("F-3", "비밀번호가 일치하지 않습니다."));
        }

        log.debug("Util.json.toStr(member.getAccessTokenClaims()) : " + Util.json.toStr(member.getAccessTokenClaims()));

        String accessToken = memberService.genAccessToken(member);

        return Util.spring.responseEntityOf(
                RsData.of(
                        "S-1",
                        "로그인 성공, Access Token을 발급합니다.",
                        Util.mapOf(
                                "accessToken", accessToken
                        )
                ),
                Util.spring.httpHeadersOf("Authentication", accessToken)
        );
    }
    @GetMapping("/me")
    @Operation(summary =  "로그인된 회원의 정보", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<RsData> me() {
        Member member = rq.getMember();
        MemberResponse memberResponse = (mapper.map(member, MemberResponse.class));

        return Util.spring.responseEntityOf(
                RsData.successOf(
                        Util.mapOf("member", memberResponse)
                )
        );
    }
}
