package com.mutbook.week3_mission.app.domain.postkeyword.repository;


import com.mutbook.week3_mission.app.domain.postkeyword.entity.PostKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostKeywordRepository extends JpaRepository<PostKeyword, Long>, PostKeywordRepositoryCustom {
    Optional<PostKeyword> findByContent(String keywordContent);
}
