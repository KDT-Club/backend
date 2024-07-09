package com.ac.su.community.post;

import com.ac.su.member.Member;
import com.ac.su.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board/{boardId}/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping
    public String createPostForm(@PathVariable Long boardId, Model model) {
        model.addAttribute("postDTO", new PostDTO());
        model.addAttribute("boardId", boardId);
        return "CreatePost";
    }

    @PostMapping
    public String createPost(@PathVariable Long boardId, @ModelAttribute PostDTO postDTO, @AuthenticationPrincipal User user) {
        // 하드코딩된 사용자 정보를 사용
        int studentId = 123456; // 예시로 사용될 하드코딩된 studentId
        Member member = memberRepository.findByStudentId(studentId).orElseThrow(() -> new IllegalArgumentException("Invalid user"));
        // 현재 로그인한 사용자의 학번(studentId)으로 사용자 조회
        // int studentId = Integer.parseInt(user.getUsername()); // 사용자 이름 대신 학번 사용
        // Member member = memberRepository.findByStudentId(studentId).orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        postService.createPost(postDTO, member, boardId);
        return "redirect:/board/" + boardId + "/posts"; // 게시글 작성 후 게시글 목록으로 리다이렉트
    }
}
