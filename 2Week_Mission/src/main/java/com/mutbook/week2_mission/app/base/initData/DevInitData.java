package com.mutbook.week2_mission.app.base.initData;

import com.mutbook.week2_mission.app.domain.member.service.MemberService;
import com.mutbook.week2_mission.app.domain.post.service.PostService;
import com.mutbook.week2_mission.app.domain.product.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevInitData implements InitDataBefore{
    @Bean
    CommandLineRunner initData(
            MemberService memberService,
            PostService postService,
            ProductService productService) {
        return args -> {
            before(memberService, postService, productService);
        };
    }
}