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

    // 자유 게시판 글 작성 폼
    @GetMapping("/board/1/posts")
    public String createGeneralPostForm(Model model, @AuthenticationPrincipal User user) {
        // 하드코딩된 사용자 정보를 사용
        int studentId = 111111; // 예시로 사용될 하드코딩된 studentId
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        model.addAttribute("postDTO", new PostDTO());
        model.addAttribute("boardId", 1L);

        return "CreatePost";
    }

    // 자유 게시판 글 작성 처리
    @PostMapping("/board/1/posts")
    public String createGeneralPost(@ModelAttribute PostDTO postDTO, @AuthenticationPrincipal User user) {
        // 하드코딩된 사용자 정보를 사용
        int studentId = 111111; // 예시로 사용될 하드코딩된 studentId
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        postService.createPost(postDTO, member, 1L, null);

        return "redirect:/board/1/posts";
    }

    // 동아리 활동 게시판 글 작성 폼
    @GetMapping("/board/3/club/{clubId}/posts")
    public String createActivityPostForm(@PathVariable Long clubId, Model model, @AuthenticationPrincipal User user) {
        // 하드코딩된 사용자 정보를 사용
        int studentId = 111111; // 예시로 사용될 하드코딩된 studentId
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        // 동아리장인지 확인
        if (member.getManagedClub() == null || !member.getManagedClub().getId().equals(clubId)) {
            throw new IllegalArgumentException("You do not have permission to create a post in this club.");
        }

        PostDTO postDTO = new PostDTO();
        postDTO.setClubName(member.getManagedClub().getName());

        model.addAttribute("postDTO", postDTO);
        model.addAttribute("boardId", 3L);
        model.addAttribute("clubId", clubId);

        return "CreatePost";
    }

    // 동아리 활동 게시판 글 작성 처리
    @PostMapping("/board/3/club/{clubId}/posts")
    public String createActivityPost(@PathVariable Long clubId, @ModelAttribute PostDTO postDTO, @AuthenticationPrincipal User user) {
        // 하드코딩된 사용자 정보를 사용
        int studentId = 111111; // 예시로 사용될 하드코딩된 studentId
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        postService.createPost(postDTO, member, 3L, clubId);

        return "redirect:/board/3/club/" + clubId + "/posts";
    }

    // 동아리 공지사항 게시판 글 작성 폼
    @GetMapping("/club/{clubId}/board/2/posts")
    public String createNoticePostForm(@PathVariable Long clubId, Model model, @AuthenticationPrincipal User user) {
        // 하드코딩된 사용자 정보를 사용
        int studentId = 111111; // 예시로 사용될 하드코딩된 studentId
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        // 동아리장인지 확인
        if (member.getManagedClub() == null || !member.getManagedClub().getId().equals(clubId)) {
            throw new IllegalArgumentException("You do not have permission to create a post in this club.");
        }

        PostDTO postDTO = new PostDTO();
        postDTO.setClubName(member.getManagedClub().getName());

        model.addAttribute("postDTO", postDTO);
        model.addAttribute("boardId", 2L);
        model.addAttribute("clubId", clubId);

        return "CreatePost";
    }

    // 동아리 공지사항 게시판 글 작성 처리
    @PostMapping("/club/{clubId}/board/2/posts")
    public String createNoticePost(@PathVariable Long clubId, @ModelAttribute PostDTO postDTO, @AuthenticationPrincipal User user) {
        // 하드코딩된 사용자 정보를 사용
        int studentId = 111111; // 예시로 사용될 하드코딩된 studentId
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));

        postService.createPost(postDTO, member, 2L, clubId);

        return "redirect:/club/" + clubId + "/board/2/posts";
    }


    // 동아리 내 자유 게시판 글 작성 폼

}
