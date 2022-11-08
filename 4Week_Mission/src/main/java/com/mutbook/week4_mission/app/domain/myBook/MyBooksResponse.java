package com.mutbook.week4_mission.app.domain.myBook;

import com.mutbook.week4_mission.app.domain.myBook.entity.MyBook;
import com.mutbook.week4_mission.app.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyBooksResponse{
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Long ownerId;
    private Product product;
}
