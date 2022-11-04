package com.mutbook.week3_mission.app.domain.cart.service;

import com.mutbook.week3_mission.app.domain.cart.entity.CartItem;
import com.mutbook.week3_mission.app.domain.cart.repository.CartItemRepository;
import com.mutbook.week3_mission.app.domain.member.entity.Member;
import com.mutbook.week3_mission.app.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartItemRepository cartItemRepository;

    public List<CartItem> getItemsByMember(Member member) {
        return cartItemRepository.findAllByMemberId(member.getId());
    }
    public CartItem addItem(Member member, Product product) {
        CartItem exCartItem = cartItemRepository.findByMemberIdAndProductId(member.getId(), product.getId()).orElse(null);

        if ( exCartItem != null ) {
            // 이미 장바구니에 담겨있음.
            return exCartItem;
        }

        CartItem cartItem = CartItem.builder()
                .member(member)
                .product(product)
                .build();

        cartItemRepository.save(cartItem);

        return cartItem;
    }
    @Transactional
    public void removeItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public void removeItem(
            Member buyer,
            Long productId
    ) {
        Product product = new Product(productId);
        removeItem(buyer, product);
    }
    @Transactional
    public boolean removeItem(Member buyer, Product product) {
        CartItem oldCartItem = cartItemRepository.findByMemberIdAndProductId(buyer.getId(), product.getId()).orElse(null);

        if (oldCartItem != null) {
            cartItemRepository.delete(oldCartItem);
            return true;
        }

        return false;
    }
    public Optional<CartItem> findItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    public boolean actorCanDelete(Member buyer, CartItem cartItem) {
        return buyer.getId().equals(cartItem.getMember().getId());
    }
}
