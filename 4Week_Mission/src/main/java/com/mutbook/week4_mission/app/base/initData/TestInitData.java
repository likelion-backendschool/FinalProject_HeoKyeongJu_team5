package com.mutbook.week4_mission.app.base.initData;

import com.mutbook.week4_mission.app.domain.member.repository.MemberRepository;
import com.mutbook.week4_mission.app.domain.member.service.MemberService;
import com.mutbook.week4_mission.app.domain.post.service.PostService;
import com.mutbook.week4_mission.app.domain.product.service.ProductService;
import com.mutbook.week4_mission.app.domain.withdraw.service.WithdrawService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestInitData implements InitDataBefore{
    @Bean
    CommandLineRunner initData(
            MemberService memberService,
            PostService postService,
            ProductService productService,
            MemberRepository memberRepository,
            WithdrawService withdrawService) {
        return args -> {
            before(memberService, postService,productService,memberRepository,withdrawService);
        };
    }
}
