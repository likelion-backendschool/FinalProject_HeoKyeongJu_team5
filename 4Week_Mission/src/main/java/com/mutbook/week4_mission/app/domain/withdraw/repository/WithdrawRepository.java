package com.mutbook.week4_mission.app.domain.withdraw.repository;

import com.mutbook.week4_mission.app.domain.withdraw.entity.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawRepository extends JpaRepository<Withdraw,Long> {
    List<Withdraw> findAllByMemberId(Long memberId);
}
