package com.mutbook.week3_mission.app.domain.withdraw.service;

import com.mutbook.week3_mission.app.domain.member.entity.AuthLevel;
import com.mutbook.week3_mission.app.domain.member.entity.Member;
import com.mutbook.week3_mission.app.domain.rebate.entity.RebateOrderItem;
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
    public Withdraw apply(String accountNumber, String bank, long withdrawAmount){
        Withdraw withdrawInfo = Withdraw.builder()
                .accountNumber(accountNumber)
                .bank(bank)
                .withdrawAmount(withdrawAmount)
                .build();

        withdrawRepository.save(withdrawInfo);
        return withdrawInfo;
    }

    public List<Withdraw> findAllByMember(Member member) {
        List<Withdraw> withdraws= withdrawRepository.findAllByMemberId(member.getId());
        return withdraws;
    }
}


