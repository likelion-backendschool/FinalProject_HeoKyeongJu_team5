package com.mutbook.week1_mission.app.domain.post.service;

import com.mutbook.week1_mission.app.domain.member.entity.Member;
import com.mutbook.week1_mission.app.domain.post.entity.Post;
import com.mutbook.week1_mission.app.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    @Transactional
    public Post write(String subject, String content, String contentHtml, Member author) {
        Post post = Post.builder()
                .subject(subject)
                .content(content)
                .contentHtml(contentHtml)
                .authorId(author.getId())
                .build();
        postRepository.save(post);

        return post;
    }
}
