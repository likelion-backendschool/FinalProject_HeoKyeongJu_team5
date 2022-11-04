package com.mutbook.week3_mission.service;

import com.mutbook.week3_mission.app.domain.member.entity.Member;
import com.mutbook.week3_mission.app.domain.member.service.MemberService;
import com.mutbook.week3_mission.app.domain.post.entity.Post;
import com.mutbook.week3_mission.app.domain.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        Post post = postService.write("제목123","내용123","<html>내용123</html>", author,"#html");

        assertThat(post).isNotNull();
        assertThat(post.getSubject()).isEqualTo("제목123");
        assertThat(post.getContent()).isEqualTo("내용123");
        assertThat(post.getContentHtml()).isEqualTo("<html>내용123</html>");
        assertThat(post.getAuthor().getId()).isEqualTo(author.getId());

    }

    @Test
    @DisplayName("글 수정")
    void test2() {
        Post post = postService.findById(1L).get();
        postService.modify(post, "제목 new", "### 내용 new","<h3>내용 new</h3>","#html");

        assertThat(post).isNotNull();
        assertThat(post.getSubject()).isEqualTo("제목 new");
        assertThat(post.getContent()).isEqualTo("### 내용 new");
    }
    @Test
    @DisplayName("글 목록 가져오기")
    void test3() {
        List<Post> postList = postService.findAll();

        assertThat(postList.size()).isEqualTo(3);
    }
    @Test
    @DisplayName("특정 글 가져오기")
    void test4() {
        Post post = postService.findById(2L).get();
        assertThat(post).isNotNull();
        assertThat(post.getSubject()).isEqualTo("제목 2");
    }
}
