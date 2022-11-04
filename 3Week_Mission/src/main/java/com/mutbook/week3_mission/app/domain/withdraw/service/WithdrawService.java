package com.mutbook.week3_mission.app.domain.withdraw.service;

import com.mutbook.week3_mission.app.domain.member.entity.Member;
import com.mutbook.week3_mission.app.domain.withdraw.entity.ApplyStatus;
import com.mutbook.week3_mission.app.domain.withdraw.entity.Withdraw;
import com.mutbook.week3_mission.app.domain.withdraw.repository.WithdrawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WithdrawService {
    private final WithdrawRepository withdrawRepository;
    public Withdraw apply(Member member, String accountNumber, String bank, long withdrawAmount){
        long cash = member.getCash();
        long amount = withdrawAmount;

        if (amount > cash) {
            throw new RuntimeException("예치금이 부족합니다.");
        }

        Withdraw withdrawInfo = Withdraw.builder()
                .accountNumber(accountNumber)
                .bank(bank)
                .withdrawAmount(withdrawAmount)
                .member(member)
                .applyStatus(ApplyStatus.READY_STATUS)
                .build();

        withdrawRepository.save(withdrawInfo);
        return withdrawInfo;
    }

    public List<Withdraw> findAllByMember(Member member) {
        List<Withdraw> withdraws= withdrawRepository.findAllByMemberId(member.getId());
        return withdraws;
    }

    public List<Withdraw> findAll() {
        return withdrawRepository.findAll();
    }
}


