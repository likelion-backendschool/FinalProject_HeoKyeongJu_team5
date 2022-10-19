package com.mutbook.week1_mission.app.domain.post.service;

import com.mutbook.week1_mission.app.domain.member.entity.Member;
import com.mutbook.week1_mission.app.domain.post.entity.Post;
import com.mutbook.week1_mission.app.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.Optional;

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
                .author(author)
                .build();
        postRepository.save(post);

        return post;
    }
    @Transactional
    public void modify(Post post, String subject, String content, String contentHtml) {
        post.setSubject(subject);
        post.setContent(content);
        post.setContentHtml(contentHtml);

    }
    @Transactional(readOnly = true)
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public boolean actorCanModify(Member author, Post post) {
        return author.getId().equals(post.getAuthor().getId());
    }
}
