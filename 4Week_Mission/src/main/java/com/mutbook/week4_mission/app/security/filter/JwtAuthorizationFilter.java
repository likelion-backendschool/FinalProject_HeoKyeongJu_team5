package com.mutbook.week4_mission.app.security.filter;

import com.mutbook.week4_mission.app.base.rq.Rq;
import com.mutbook.week4_mission.app.domain.member.entity.Member;
import com.mutbook.week4_mission.app.domain.member.service.MemberService;
import com.mutbook.week4_mission.app.security.jwt.JwtProvider;
import com.mutbook.week4_mission.app.security.dto.MemberContext;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final MemberService memberService;
    private final Rq rq;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null) {
            String token = bearerToken.substring("Bearer ".length());

            // 1차 체크(정보가 변조되지 않았는지 체크)
            if (jwtProvider.verify(token)) {
                Map<String, Object> claims = jwtProvider.getClaims(token);
                Member member = memberService.findByUsername((String) claims.get("username")).get();

                // 2차 체크(화이트리스트에 포함되는지)
                if (memberService.verifyWithWhiteList(member, token)) {
                    forceAuthentication(member);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void forceAuthentication(Member member) {
        MemberContext memberContext = new MemberContext(member, member.genAuthorities());

        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        memberContext,
                        null,
                        member.genAuthorities()
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
