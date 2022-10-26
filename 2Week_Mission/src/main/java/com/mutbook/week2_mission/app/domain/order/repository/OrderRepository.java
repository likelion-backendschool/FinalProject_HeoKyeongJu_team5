package com.mutbook.week2_mission.app.domain.order.repository;

import com.mutbook.week2_mission.app.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
