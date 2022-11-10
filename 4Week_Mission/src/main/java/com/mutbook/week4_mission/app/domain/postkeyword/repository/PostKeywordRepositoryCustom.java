package com.mutbook.week4_mission.app.domain.postkeyword.repository;


import com.mutbook.week4_mission.app.domain.postkeyword.entity.PostKeyword;

import java.util.List;

public interface PostKeywordRepositoryCustom {
    List<PostKeyword> getQslAllByAuthorId(Long authorId);
}
