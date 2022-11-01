package com.mutbook.week3_mission.app.domain.rebate.repository;

import com.mutbook.week3_mission.app.domain.rebate.entity.RebateOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RebateOrderItemRepository extends JpaRepository<RebateOrderItem,Long> {
    Optional<RebateOrderItem> findByOrderItemId(long orderItemId);
}
