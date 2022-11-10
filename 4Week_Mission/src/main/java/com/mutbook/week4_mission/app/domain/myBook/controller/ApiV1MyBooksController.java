package com.mutbook.week4_mission.app.domain.myBook.controller;

import com.mutbook.week4_mission.app.base.dto.RsData;
import com.mutbook.week4_mission.app.base.rq.Rq;
import com.mutbook.week4_mission.app.domain.member.entity.Member;
import com.mutbook.week4_mission.app.domain.myBook.MyBooksResponse;
import com.mutbook.week4_mission.app.domain.myBook.entity.MyBook;
import com.mutbook.week4_mission.app.domain.myBook.service.MyBookService;
import com.mutbook.week4_mission.util.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/myBooks", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "ApiV1MyBooksController", description = "로그인 된 회윈이 구매한 책 정보")
public class ApiV1MyBooksController {
    private final ModelMapper mapper;
    private final MyBookService myBookService;
    private final Rq rq;

    @GetMapping(value = "", consumes = ALL_VALUE)
    @Operation(summary =  "로그인된 회원이 보유한 도서 목록", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<RsData> myBooks(){
        Member owner = rq.getMember();
        List<MyBooksResponse> myBooks = myBookService.findAllByOwnerId(owner.getId()).stream().map(myBook -> mapper.map(myBook,MyBooksResponse.class)).toList();

        return Util.spring.responseEntityOf(
                RsData.successOf(
                        Util.mapOf("myBooks", myBooks)
                )
        );
    }
}
