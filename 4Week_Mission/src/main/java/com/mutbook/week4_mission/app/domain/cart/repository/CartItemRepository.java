package com.mutbook.week4_mission.app.domain.cart.repository;

import com.mutbook.week4_mission.app.domain.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findAllByMemberId(Long id);

    Optional<CartItem> findByMemberIdAndProductId(Long memberId, Long productId);
}
