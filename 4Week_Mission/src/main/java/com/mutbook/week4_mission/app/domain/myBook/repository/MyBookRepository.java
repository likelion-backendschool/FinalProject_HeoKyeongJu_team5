package com.mutbook.week4_mission.app.domain.myBook.repository;

import com.mutbook.week4_mission.app.domain.myBook.entity.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyBookRepository extends JpaRepository<MyBook, Long> {
    void deleteByProductIdAndOwnerId(long productId, long ownerId);
}
