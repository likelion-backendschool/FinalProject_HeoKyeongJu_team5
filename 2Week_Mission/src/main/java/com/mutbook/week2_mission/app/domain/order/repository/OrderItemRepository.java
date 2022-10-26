package com.mutbook.week2_mission.app.domain.order.repository;

import com.mutbook.week2_mission.app.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
