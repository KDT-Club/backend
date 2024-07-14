package com.ac.su.community.post;

import com.ac.su.member.Member;
import com.ac.su.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberRepository memberRepository;

    private Member getAuthenticatedMember(@AuthenticationPrincipal User user) {
        int studentId = Integer.parseInt(user.getUsername());
        return memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
    }

    // 자유 게시판 글 작성 처리
    @PostMapping("/board/1/posts")
    public ResponseEntity<String> createGeneralPost(@RequestPart("postDTO") PostDTO postDTO, @RequestPart("files") MultipartFile[] files, @AuthenticationPrincipal User user) {
        try {
            Member member = getAuthenticatedMember(user);
            postService.createPost(postDTO, files, member, 1L, null);
            return ResponseEntity.ok("게시글 작성 성공!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 작성 실패! 에러 메시지: " + e.getMessage());
        }
    }

    // 동아리 활동 게시판 글 작성 처리
    @PostMapping("/board/3/club/{clubId}/posts")
    public ResponseEntity<String> createActivityPost(@PathVariable Long clubId, @RequestPart("postDTO") PostDTO postDTO, @RequestPart("files") MultipartFile[] files, @AuthenticationPrincipal User user) {
        try {
            Member member = getAuthenticatedMember(user);
            postService.createPost(postDTO, files, member, 3L, clubId);
            return ResponseEntity.ok("게시글 작성 성공!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 작성 실패! 에러 메시지: " + e.getMessage());
        }
    }

    // 동아리 공지사항 게시판 글 작성 처리
    @PostMapping("/club/{clubId}/board/2/posts")
    public ResponseEntity<String> createNoticePost(@PathVariable Long clubId, @RequestPart("postDTO") PostDTO postDTO, @RequestPart("files") MultipartFile[] files, @AuthenticationPrincipal User user) {
        try {
            Member member = getAuthenticatedMember(user);
            postService.createPost(postDTO, files, member, 2L, clubId);
            return ResponseEntity.ok("게시글 작성 성공!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 작성 실패! 에러 메시지: " + e.getMessage());
        }
    }

    // 동아리 내부 자유게시판 글 작성 처리
    @PostMapping("/club/{clubId}/board/4/posts")
    public ResponseEntity<String> createInternalPost(@PathVariable Long clubId, @RequestPart("postDTO") PostDTO postDTO, @RequestPart("files") MultipartFile[] files, @AuthenticationPrincipal User user) {
        try {
            Member member = getAuthenticatedMember(user);
            postService.createPost(postDTO, files, member, 4L, clubId);
            return ResponseEntity.ok("게시글 작성 성공!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 작성 실패! 에러 메시지: " + e.getMessage());
        }
    }
}
