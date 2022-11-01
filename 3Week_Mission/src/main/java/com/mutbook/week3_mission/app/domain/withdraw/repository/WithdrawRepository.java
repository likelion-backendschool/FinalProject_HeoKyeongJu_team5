package com.mutbook.week3_mission.app.domain.withdraw.repository;

import com.mutbook.week3_mission.app.domain.withdraw.entity.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawRepository extends JpaRepository<Withdraw,Long> {
}
