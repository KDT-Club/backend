package com.ac.su.community.post;

import com.ac.su.member.Member;
import com.ac.su.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberRepository memberRepository;

    // 자유게시판
    @GetMapping("/board/{boardId}/posts")
    public String createPostForm(@PathVariable Long boardId, Model model, @AuthenticationPrincipal User user) {
        // 하드코딩된 사용자 정보를 사용
        int studentId = 111111; // 예시로 사용될 하드코딩된 studentId
        Member member = memberRepository.findByStudentId(studentId).orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        model.addAttribute("postDTO", new PostDTO());
        model.addAttribute("boardId", boardId);
        return "CreatePost";
    }

    @PostMapping("/board/{boardId}/posts")
    public String createPost(@PathVariable Long boardId, @ModelAttribute PostDTO postDTO, @AuthenticationPrincipal User user) {
        // 하드코딩된 사용자 정보를 사용
        int studentId = 111111; // 예시로 사용될 하드코딩된 studentId
        Member member = memberRepository.findByStudentId(studentId).orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        postService.createPost(postDTO, member, boardId);
        return "redirect:/board/" + boardId + "/posts"; // 게시글 작성 후 게시글 목록으로 리다이렉트
    }

    // 활동게시판
    @GetMapping("/board/{boardId}/club/{clubId}/posts")
    public String createClubPostForm(@PathVariable Long boardId, @PathVariable Long clubId, Model model, @AuthenticationPrincipal User user) {
        // 하드코딩된 사용자 정보를 사용
        int studentId = 111111; // 예시로 사용될 하드코딩된 studentId
        Member member = memberRepository.findByStudentId(studentId).orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        if (member.getManagedClub() == null || !member.getManagedClub().getId().equals(clubId)) {
            throw new IllegalArgumentException("You do not have permission to create a post in this club.");
        }

        model.addAttribute("postDTO", new PostDTO());
        model.addAttribute("boardId", boardId);
        model.addAttribute("clubId", clubId);
        model.addAttribute("clubName", member.getManagedClub().getName());
        return "CreatePost";
    }

    @PostMapping("/board/{boardId}/club/{clubId}/posts")
    public String createClubPost(@PathVariable Long boardId, @PathVariable Long clubId, @ModelAttribute PostDTO postDTO, @AuthenticationPrincipal User user) {
        // 하드코딩된 사용자 정보를 사용
        int studentId = 111111; // 예시로 사용될 하드코딩된 studentId
        Member member = memberRepository.findByStudentId(studentId).orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        if (member.getManagedClub() == null || !member.getManagedClub().getId().equals(clubId)) {
            throw new IllegalArgumentException("You do not have permission to create a post in this club.");
        }

        postService.createPost(postDTO, member, boardId);
        return "redirect:/board/" + boardId + "/club/" + clubId + "/posts"; // 게시글 작성 후 게시글 목록으로 리다이렉트
    }
}
