package com.mutbook.week4_mission.app.base.initData;

import com.mutbook.week4_mission.app.domain.cart.service.CartService;
import com.mutbook.week4_mission.app.domain.member.repository.MemberRepository;
import com.mutbook.week4_mission.app.domain.member.service.MemberService;
import com.mutbook.week4_mission.app.domain.myBook.service.MyBookService;
import com.mutbook.week4_mission.app.domain.order.service.OrderService;
import com.mutbook.week4_mission.app.domain.post.service.PostService;
import com.mutbook.week4_mission.app.domain.product.service.ProductService;
import com.mutbook.week4_mission.app.domain.withdraw.service.WithdrawService;
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
            ProductService productService,
            MemberRepository memberRepository,
            WithdrawService withdrawService,
            OrderService orderService,
            MyBookService myBookService,
            CartService cartService) {
        return args -> {
            before(memberService, postService, productService,memberRepository, withdrawService,orderService,myBookService,cartService);
        };
    }
}