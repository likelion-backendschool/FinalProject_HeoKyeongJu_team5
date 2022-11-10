package com.mutbook.week4_mission.app.domain.cash.repository;


import com.mutbook.week4_mission.app.domain.cash.entity.CashLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashLogRepository extends JpaRepository<CashLog, Long> {
}
