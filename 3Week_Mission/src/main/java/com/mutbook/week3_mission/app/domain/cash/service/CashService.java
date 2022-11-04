package com.mutbook.week3_mission.app.domain.cash.service;

import com.mutbook.week3_mission.app.domain.cash.entity.CashLog;
import com.mutbook.week3_mission.app.domain.cash.repository.CashLogRepository;
import com.mutbook.week3_mission.app.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashService {
    private final CashLogRepository cashLogRepository;

    public CashLog addCash(Member member, long price, String eventType) {
        CashLog cashLog = CashLog.builder()
                .member(member)
                .price(price)
                .eventType(eventType)
                .build();

        cashLogRepository.save(cashLog);

        return cashLog;
    }
}
