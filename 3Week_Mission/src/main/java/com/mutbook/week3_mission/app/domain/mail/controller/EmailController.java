package com.mutbook.week3_mission.app.domain.mail.controller;

import com.mutbook.week3_mission.app.domain.mail.service.EmailService;
import com.mutbook.week3_mission.app.domain.member.entity.Member;
import com.mutbook.week3_mission.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    @PostMapping("/mail")
    public void sendJoinMail(MemberContext memberContext, Member member) {
        emailService.sendJoinMail(member);
    }
}
