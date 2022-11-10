package com.mutbook.week4_mission.app.domain.order.repository;

import com.mutbook.week4_mission.app.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByBuyerId(Long id);
    List<Order> findAllByBuyerIdOrderByIdDesc(Long memberId);
}
