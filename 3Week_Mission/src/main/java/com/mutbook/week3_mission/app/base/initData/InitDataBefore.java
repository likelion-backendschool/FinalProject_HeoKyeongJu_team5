package com.mutbook.week3_mission.app.base.initData;

import com.mutbook.week3_mission.app.domain.member.entity.AuthLevel;
import com.mutbook.week3_mission.app.domain.member.entity.Member;
import com.mutbook.week3_mission.app.domain.member.repository.MemberRepository;
import com.mutbook.week3_mission.app.domain.member.service.MemberService;
import com.mutbook.week3_mission.app.domain.post.service.PostService;
import com.mutbook.week3_mission.app.domain.product.entity.Product;
import com.mutbook.week3_mission.app.domain.product.service.ProductService;
import com.mutbook.week3_mission.app.domain.withdraw.service.WithdrawService;

public interface InitDataBefore {
    default void before(MemberService memberService, PostService postService, ProductService productService, MemberRepository memberRepository, WithdrawService withdrawService) {
        Member member1 = memberService.join("user1", "1234", "user1@test.com","꿀벌");
        Member member2 = memberService.join("user2", "1234", "user2@test.com", null);
        Member admin = memberService.join("허경주", "1234", "beewt@naver.com",null);
        admin.setAuthLevel(AuthLevel.ADMIN); // 관리자 권한 부여
        memberService.beAuthor(member1, member1.getNickname());
        memberRepository.save(member1);
        memberRepository.save(admin);
        postService.write(
                "자바를 우아하게 사용하는 방법",
                "# 내용 1",
                "<h1>내용 1</h1>",
                member1,
                "#자바"
        );

        postService.write(
                "자바스크립트를 우아하게 사용하는 방법",
                """
                        # 자바스크립트는 이렇게 쓰세요.
                                                
                        ```js
                        const a = 10;
                        console.log(a);
                        ```
                        """.stripIndent(),
                """
                        <h1>자바스크립트는 이렇게 쓰세요.</h1><div data-language="js" class="toastui-editor-ww-code-block-highlighting"><pre class="language-js"><code data-language="js" class="language-js"><span class="token keyword">const</span> a <span class="token operator">=</span> <span class="token number">10</span><span class="token punctuation">;</span>
                        <span class="token console class-name">console</span><span class="token punctuation">.</span><span class="token method function property-access">log</span><span class="token punctuation">(</span>a<span class="token punctuation">)</span><span class="token punctuation">;</span></code></pre></div>
                                                """.stripIndent(),

                member1,
                "#IT #프론트엔드 #리액트"
        );

        postService.write( "제목 3", "내용 3", "내용 3", member1,"#IT# 프론트엔드 #HTML #CSS");
        postService.write( "제목 4", "내용 4", "내용 4", member1,"#IT #스프링부트 #자바");
        postService.write( "제목 5", "내용 5", "내용 5", member1,"#IT #자바 #카프카");
        postService.write( "제목 6", "내용 6", "내용 6", member1,"#IT #프론트엔드 #REACT");
        postService.write( "제목 7", "내용 7", "내용 7", member1,"#IT# 프론트엔드 #HTML #CSS");
        postService.write("제목 8", "내용 8", "내용 8",member1 ,"#IT #스프링부트 #자바");

        Product product1 = productService.create(member1, "상품명1 상품명1 상품명1 상품명1 상품명1 상품명1", 30_000, "카프카", "#IT #카프카");
        Product product2 = productService.create(member2, "상품명2", 40_000, "스프링부트", "#IT #REACT");
        Product product3 = productService.create(member1, "상품명3", 50_000, "REACT", "#IT #REACT");
        Product product4 = productService.create(member2, "상품명4", 60_000, "HTML", "#IT #HTML");

        memberService.addCash(member1, 100_000L, "충전__무통장입금");
        memberService.addCash(member1, 20_000L, "충전__무통장입금");
        memberService.addCash(member1, -5_000L, "출금__일반");
        memberService.addCash(member1, 1_000_000L, "충전__무통장입금");
        memberService.addCash(member2, 2_000_000L, "충전__무통장입금");

        withdrawService.apply(member1,"123412341234","카카오뱅크",10000L);
        withdrawService.apply(member2,"987098709870","신협",8000L);
        withdrawService.apply(member1,"123412341234","카카오뱅크",5000L);

    }
}
