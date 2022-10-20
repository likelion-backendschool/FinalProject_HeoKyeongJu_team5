package com.mutbook.week1_mission.app.security.service;

import com.mutbook.week1_mission.app.domain.member.entity.Member;
import com.mutbook.week1_mission.app.domain.member.entity.Type;
import com.mutbook.week1_mission.app.domain.member.repository.MemberRepository;
import com.mutbook.week1_mission.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Type.USER.toString()));

        return new MemberContext(member, authorities);
    }
}