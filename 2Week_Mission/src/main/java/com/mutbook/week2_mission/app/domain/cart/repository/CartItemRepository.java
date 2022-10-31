package com.mutbook.week2_mission.app.domain.cart.repository;

import com.mutbook.week2_mission.app.domain.cart.entity.CartItem;
import com.mutbook.week2_mission.app.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findAllByMemberId(Long id);

    Optional<CartItem> findByMemberIdAndProductId(Long memberId, Long productId);
}
