package com.mutbook.week4_mission.app.domain.postkeyword.service;

import com.mutbook.week4_mission.app.domain.postkeyword.entity.PostKeyword;
import com.mutbook.week4_mission.app.domain.postkeyword.repository.PostKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostKeywordService {
    private final PostKeywordRepository postKeywordRepository;

    public PostKeyword save(String content) {
        Optional<PostKeyword> optKeyword = postKeywordRepository.findByContent(content);

        if (optKeyword.isPresent()) {
            return optKeyword.get();
        }

        PostKeyword postKeyword = PostKeyword
                .builder()
                .content(content)
                .build();

        postKeywordRepository.save(postKeyword);

        return postKeyword;
    }

    public Optional<PostKeyword> findByContent(String content) {
        return postKeywordRepository.findByContent(content);
    }

    public PostKeyword findByContentOrSave(String content) {
        return save(content);
    }

    public Optional<PostKeyword> findById(long id) {
        return postKeywordRepository.findById(id);
    }

    public List<PostKeyword> findByMemberId(Long authorId) {
        return postKeywordRepository.getQslAllByAuthorId(authorId);
    }
}
