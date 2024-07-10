package com.ac.su.clubmember;

import com.ac.su.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
public class ClubMemberRestController {
    private final ClubMemberService clubMemberService;
    private final ClubMemberRepository clubMemberRepository;

    // 동아리 id에 따른 동아리 회원 리스트 출력
    @GetMapping("/{clubId}/clubMember")
    public ResponseEntity<?> clubMemberList(@PathVariable("clubId") Long clubId) {
        List<ClubMemberDTO> clubMemberDTOList = clubMemberService.findAllByClubId(clubId);
        return ResponseEntity.ok(clubMemberDTOList);
    }

    // 회원 상세 정보
    @GetMapping("/{clubId}/clubMember/{memberId}")
    public ResponseEntity<?> memberDetail(@PathVariable("memberId") Long memberId,
                                          @PathVariable("clubId") Long clubId) {
        ClubMemberDTO clubMemberDTO = clubMemberService.findByMemberId(memberId, clubId);
        return ResponseEntity.ok(clubMemberDTO);
    }

    // 회원 상태 수정 (status는 현재 사용자의 상태, changeStatus는 변경할 회원의 상태)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{clubId}/clubMember/{memberId}/changeStatus")
    public ResponseEntity<?> changeStatus(@PathVariable("memberId") Long memberId,
                                          @PathVariable("clubId") Long clubId,
                                          @RequestParam("status") MemberStatus status,
                                          @RequestParam("changeStatus") MemberStatus changeStatus,
                                          Authentication auth) {
        // 동아리 회장이 아닐 때
        if (status != MemberStatus.CLUB_PRESIDENT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("동아리 회장만 접근 가능합니다"));
        }
        // 동아리 회장일 때
        // PathVariable로 받은 동아리의 회장인지 검사
        else {
            if (!clubMemberService.existsById(Long.valueOf(auth.getName()), clubId))
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("동아리 회장만 접근 가능합니다"));
        }

        // 회원 상태 수정
        clubMemberService.changeStatus(memberId, clubId, changeStatus);
        return ResponseEntity.ok("회원 등급이 성공적으로 변경되었습니다.");
    }

    // 회원 삭제
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{clubId}/clubMember/{memberId}/deleteMember")
    public ResponseEntity<?> deleteMember(@PathVariable("memberId") Long memberId,
                                          @PathVariable("clubId") Long clubId,
                                          @RequestParam("status") MemberStatus status,
                                          Authentication auth) {
        // 동아리 회장이 아닐 때
        if (status != MemberStatus.CLUB_PRESIDENT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("동아리 회장만 접근 가능합니다"));
        }
        // 동아리 회장일 때
        // PathVariable로 받은 동아리의 회장인지 검사
        else {
            if (!clubMemberService.existsById(Long.valueOf(auth.getName()), clubId))
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("동아리 회장만 접근 가능합니다"));
        }

        // 회원 삭제
        clubMemberService.deleteMember(memberId, clubId);
        return ResponseEntity.ok("회원을 탈퇴시켰습니다.");
    }
}