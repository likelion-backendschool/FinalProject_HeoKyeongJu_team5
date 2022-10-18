package com.mutbook.week1_mission.service;

import com.mutbook.week1_mission.app.domain.member.entity.Member;
import com.mutbook.week1_mission.app.domain.member.service.MemberService;
import com.mutbook.week1_mission.app.domain.post.entity.Post;
import com.mutbook.week1_mission.app.domain.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")

public class PostServiceTests {
    @Autowired
    PostService postService;
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("글 생성")
    void test1() {
        Member author = memberService.findByUsername("user1").get();

        Post post = postService.write("제목1","내용1","<html>내용1</html>", author);

        assertThat(post).isNotNull();
        assertThat(post.getSubject()).isEqualTo("제목1");
        assertThat(post.getContent()).isEqualTo("내용1");
        assertThat(post.getContentHtml()).isEqualTo("<html>내용1</html>");
        assertThat(post.getAuthorId()).isEqualTo(author.getId());

    }
}
