package com.mutbook.week1_mission.app.base.initData;

import com.mutbook.week1_mission.app.domain.member.entity.Member;
import com.mutbook.week1_mission.app.domain.member.service.MemberService;
import com.mutbook.week1_mission.app.domain.post.entity.Post;
import com.mutbook.week1_mission.app.domain.post.service.PostService;

import java.util.List;

public interface InitDataBefore {
    default void before(MemberService memberService, PostService postService) {
        Member member1 = memberService.join("user1", "1234", "user1@test.com");
        Member member2 = memberService.join("user2", "1234", "user2@test.com");

        Post post1 = postService.create("제목 1", "내용 1", "<html>내용 1</html>", member1);
        Post post2 = postService.create("제목 2", "내용 2", "<html>내용 2</html>", member1);
        Post post3 = postService.create("제목 3", "내용 3", "<html>내용 3</html>", member2);

    }
}
