package com.mutbook.week1_mission.app.domain.post.controller;

import com.mutbook.week1_mission.app.domain.member.entity.Member;
import com.mutbook.week1_mission.app.domain.post.dto.PostDto;
import com.mutbook.week1_mission.app.domain.post.entity.Post;
import com.mutbook.week1_mission.app.domain.post.exception.ActorCanNotModifyException;
import com.mutbook.week1_mission.app.domain.post.service.PostService;
import com.mutbook.week1_mission.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {
    private final PostService postService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showCreate() {
        return "song/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@AuthenticationPrincipal MemberContext memberContext, @Valid PostDto postDto) {
        Member author = memberContext.getMember();
        Post post = postService.write(postDto.getSubject(), postDto.getContent(), postDto.getContentHtml(),author);
        return "redirect:/post/" + post.getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String showModify(@AuthenticationPrincipal MemberContext memberContext, @PathVariable long id, Model model) {
        Post post = postService.findById(id).get();

        Member actor = memberContext.getMember();

        if (postService.actorCanModify(actor, post) == false) {
            throw new ActorCanNotModifyException();
        }

        model.addAttribute("post", post);

        return "post/modify";
    }

//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/{id}/modify")
//    public String modify(@AuthenticationPrincipal MemberContext memberContext, @Valid SongForm songForm, @PathVariable long id) {
//        Song song = songService.findById(id).get();
//        Member actor = memberContext.getMember();
//
//        if (songService.actorCanModify(actor, song) == false) {
//            throw new ActorCanNotModifyException();
//        }
//
//        songService.modify(song, songForm.getSubject(), songForm.getContent());
//        return "redirect:/song/" + song.getId() + "?msg=" + Ut.url.encode("%d번 음원이 생성되었습니다.".formatted(song.getId()));
//    }
}
