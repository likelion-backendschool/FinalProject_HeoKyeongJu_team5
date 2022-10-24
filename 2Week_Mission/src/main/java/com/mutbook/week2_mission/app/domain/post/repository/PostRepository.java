package com.mutbook.week2_mission.app.domain.post.repository;

import com.mutbook.week2_mission.app.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository <Post, Long>{
    List<Post> findAllByAuthorIdOrderByIdDesc(long authorId);

}
