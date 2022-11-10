package com.mutbook.week4_mission.app.domain.myBook.repository;

import com.mutbook.week4_mission.app.domain.myBook.MyBooksResponse;
import com.mutbook.week4_mission.app.domain.myBook.entity.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {
    void deleteByProductIdAndOwnerId(long productId, long ownerId);

    List<MyBook> findAllByOwnerId(Long memberId);
}
