package com.mutbook.week4_mission.app.domain.myBook;

import com.mutbook.week4_mission.app.domain.member.entity.Member;
import com.mutbook.week4_mission.app.domain.myBook.entity.MyBook;
import com.mutbook.week4_mission.app.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MyBooksResponse{
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Member owner;
    private Product product;

    public MyBooksResponse(Long id, LocalDateTime createDate, LocalDateTime modifyDate, Member owner, Product product){
        this.id = id;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.owner = owner;
        this.product = product;
    }
}
