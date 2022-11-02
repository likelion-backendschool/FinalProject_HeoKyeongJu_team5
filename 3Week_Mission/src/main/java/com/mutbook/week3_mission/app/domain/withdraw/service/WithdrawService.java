package com.mutbook.week3_mission.app.domain.withdraw.service;

import com.mutbook.week3_mission.app.base.rq.Rq;
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
    private final Rq rq;
    public Withdraw apply(String accountNumber, String bank, long withdrawAmount){
        // 예치금보다 작아야한다는 로직 추가해야 함.
        Withdraw withdrawInfo = Withdraw.builder()
                .accountNumber(accountNumber)
                .bank(bank)
                .withdrawAmount(withdrawAmount)
                .member(rq.getMember())
                .build();

        withdrawRepository.save(withdrawInfo);
        return withdrawInfo;
    }

    public List<Withdraw> findAllByMember(Member member) {
        List<Withdraw> withdraws= withdrawRepository.findAllByMemberId(member.getId());
        return withdraws;
    }
}


