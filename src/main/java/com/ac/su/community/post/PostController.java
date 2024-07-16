package com.ac.su.community.post;


import com.ac.su.community.board.Board;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ac.su.member.Member;
import com.ac.su.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController

@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final MemberRepository memberRepository;
    private PostRepository postRepository;

    private Member getAuthenticatedMember(@AuthenticationPrincipal User user) {
        String studentId = user.getUsername();
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


    @GetMapping("/posts/{memberId}")
    public List<Post> getPostsByMemberId(@PathVariable Long memberId) {
        return postService.getPostsByMemberId(memberId);
    }

    // 게시물 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        boolean isDeleted = postService.deletePost(postId);
        if (isDeleted) {
            return ResponseEntity.ok("{\"message\":\"성공\"}");
        } else {
            return ResponseEntity.status(400).body("{\"message\":\"에러남\"}");
        }
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostUpdateDto postUpdateDto) {
        Optional<Post> updatedPost = postService.updatePost(postId, postUpdateDto);
        if (updatedPost.isPresent()) {
            Post post = updatedPost.get();
            return ResponseEntity.ok("{\"postId\":\"" + post.getId() + "\","
                    + "\"title\":\"" + post.getTitle() + "\","
                    + "\"content\":\"" + post.getContent() + "\","
                    + "\"attachment_flag\":\"" + post.getAttachmentFlag() + "\","
                    + "\"attachment_name\":\"" + postUpdateDto.getAttachmentName() + "\"}");
        } else {
            return ResponseEntity.status(400).body("{\"message\":\"에러남\"}");
        }
    }

}
