package com.mutbook.week1_mission.app.domain.post.controller;

import com.mutbook.week1_mission.util.Util;
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
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {
    private final PostService postService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWrite() {
        return "post/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@AuthenticationPrincipal MemberContext memberContext, @Valid PostDto postDto) {
        Member author = memberContext.getMember();
        String contentHtml = Util.markdown(postDto.getContent());
        Post post = postService.write(postDto.getSubject(), postDto.getContent(), contentHtml,author);
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String modify(@AuthenticationPrincipal MemberContext memberContext, @Valid PostDto postDto, @PathVariable long id) {
        Post post = postService.findById(id).get();
        Member actor = memberContext.getMember();
        String contentHtml = Util.markdown(postDto.getContent());
        if (postService.actorCanModify(actor, post) == false) {
            throw new ActorCanNotModifyException();
        }

        postService.modify(post, postDto.getSubject(), postDto.getContent(), contentHtml);
        return "redirect:/post/" + post.getId();
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String showList(Model model) {
        List<Post> postList = postService.findAll();
        model.addAttribute("postList", postList);
        return "post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String detail(@AuthenticationPrincipal MemberContext memberContext, @PathVariable Long id, Model model) {
        Post post = postService.findById(id).get();

        Member actor = memberContext.getMember();

        model.addAttribute("post", post);

        return "post/detail";
    }
}
