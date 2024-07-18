package com.ac.su.community.post;

import com.ac.su.ResponseMessage;
import com.ac.su.community.attachment.AttachmentFlag;
import com.ac.su.member.Member;
import com.ac.su.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberRepository memberRepository;

    // 현재 로그인한 사용자를 가져오는 메서드
    private Member getAuthenticatedMember(@AuthenticationPrincipal User user) {
        String studentId = user.getUsername();
        return memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user"));
    }

    // 자유 게시판 글 작성 처리 (URL을 입력받아 저장)
    @PostMapping("/board/1/posts")
    public ResponseEntity<ResponseMessage> createGeneralPost(@RequestBody PostDTO request, @AuthenticationPrincipal User user) {
        try {
            Member member = getAuthenticatedMember(user);
            postService.createPost(request, member, 1L, null);
            return ResponseEntity.ok(new ResponseMessage("게시글 작성 성공!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("게시글 작성 실패! 에러 메시지: " + e.getMessage()));
        }
    }

    // 동아리 활동 게시판 글 작성 처리
    @PostMapping("/board/3/club/{clubId}/posts")
    public ResponseEntity<ResponseMessage> createActivityPost(@PathVariable Long clubId, @RequestBody PostDTO request, @AuthenticationPrincipal User user) {
        try {
            Member member = getAuthenticatedMember(user);

            // 권한 체크
            if (member.getClub() == null || !member.getClub().getId().equals(clubId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage("권한이 없습니다."));
            }

            postService.createPost(request, member, 3L, clubId);
            return ResponseEntity.ok(new ResponseMessage("게시글 작성 성공!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("게시글 작성 실패! 에러 메시지: " + e.getMessage()));
        }
    }

    // 동아리 공지사항 게시판 글 작성 처리
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/club/{clubId}/board/2/posts")
    public ResponseEntity<ResponseMessage> createNoticePost(@PathVariable("clubId") Long clubId, @RequestBody PostDTO request, @AuthenticationPrincipal User user) {
        try {
            Member member = getAuthenticatedMember(user);
            System.out.println("123");
            System.out.println("유저객ㅊㅔ임" + member.getClub());
            System.out.println(member.getClub().getId());

            // 권한 체크
            if (member.getClub() == null || !member.getClub().getId().equals(clubId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage("권한이 없습니다."));
            }

            postService.createPost(request, member, 2L, clubId);
            return ResponseEntity.ok(new ResponseMessage("게시글 작성 성공!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("게시글 작성 실패! 에러 메시지: " + e.getMessage()));
        }
    }

    // 동아리 내부 자유게시판 글 작성 처리
    @PostMapping("/club/{clubId}/board/4/posts")
    public ResponseEntity<ResponseMessage> createInternalPost(@PathVariable Long clubId, @RequestBody PostDTO request, @AuthenticationPrincipal User user) {
        try {
            Member member = getAuthenticatedMember(user);

            // 권한 체크
            if (member.getClub() == null || !member.getClub().getId().equals(clubId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage("권한이 없습니다."));
            }

            postService.createPost(request, member, 4L, clubId);
            return ResponseEntity.ok(new ResponseMessage("게시글 작성 성공!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("게시글 작성 실패! 에러 메시지: " + e.getMessage()));
        }
    }

    // 회원 ID로 게시물 조회
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

    // 게시물 수정
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
