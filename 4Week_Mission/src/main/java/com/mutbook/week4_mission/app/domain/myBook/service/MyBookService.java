package com.mutbook.week4_mission.app.domain.myBook.service;

import com.mutbook.week4_mission.app.base.dto.RsData;
import com.mutbook.week4_mission.app.domain.myBook.MyBooksResponse;
import com.mutbook.week4_mission.app.domain.myBook.entity.MyBook;
import com.mutbook.week4_mission.app.domain.myBook.repository.MyBookRepository;
import com.mutbook.week4_mission.app.domain.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyBookService {
    private final MyBookRepository myBookRepository;

    @Transactional
    public RsData add(Order order) {
        order.getOrderItems()
                .stream()
                .map(orderItem -> MyBook.builder()
                        .owner(order.getBuyer())
                        .orderItem(orderItem)
                        .product(orderItem.getProduct())
                        .build())
                .forEach(myBookRepository::save);

        return RsData.of("S-1", "나의 책장에 추가되었습니다.");
    }

    @Transactional
    public RsData remove(Order order) {
        order.getOrderItems()
                .stream()
                .forEach(orderItem -> myBookRepository.deleteByProductIdAndOwnerId(orderItem.getProduct().getId(), order.getBuyer().getId()));

        return RsData.of("S-1", "나의 책장에서 제거되었습니다.");
    }

    public List<MyBook> findAllByOwnerId(Long memberId) {
        return myBookRepository.findAllByOwnerId(memberId);
    }
}
