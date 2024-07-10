package com.ac.su.joinrequest;

import com.ac.su.ResponseMessage;
import com.ac.su.clubmember.MemberStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/clubs")
public class JoinRequestRestController {
    private final JoinRequestService joinRequestService;

    // 동아리 id에 따른 동아리 지원서 리스트 출력
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{clubId}/joinRequest")
    public ResponseEntity<?>  joinRequestList(
            @PathVariable("clubId") Long requestId,
            @RequestParam("status") MemberStatus status) {
        // 동아리 회장인지 검사
        if (status!=MemberStatus.CLUB_PRESIDENT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("동아리 회장만 접근 가능합니다"));
        }

        // 동아리 지원서 리스트 출력
        List<JoinRequestDTO> joinRequestDTOList = joinRequestService.findRequestByClubId(requestId);
        return ResponseEntity.ok(joinRequestDTOList);
    }

    // 동아리 지원서 상세 정보
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{clubId}/joinRequest/{requestId}")
    public ResponseEntity<?> requestDetail(
            @PathVariable("requestId") Long requestId,
            @RequestParam("status") MemberStatus status) {
        // 동아리 회장인지 검사
        if (status!=MemberStatus.CLUB_PRESIDENT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("동아리 회장만 접근 가능합니다"));
        }

        // 동아리 지원서 상세 정보 출력
        JoinRequestDTO joinRequestDTO = joinRequestService.findByRequestId(requestId);
        return ResponseEntity.ok(joinRequestDTO);
    }

    // 가입 승인
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{clubId}/joinRequest/{requestId}/approveRequest")
    public ResponseEntity<?> approveRequest(
            @PathVariable("requestId") Long requestId,
            @PathVariable("clubId") Long clubId,
            @RequestParam("memberId") Long memberId,
            @RequestParam("status") MemberStatus status) {
        // 동아리 회장인지 검사
        if (status!=MemberStatus.CLUB_PRESIDENT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("동아리 회장만 접근 가능합니다"));
        }

        // 가입 승인
        joinRequestService.approveRequest(requestId, clubId, memberId);
        return ResponseEntity.ok("가입을 승인했습니다.");
    }

    // 가입 거절
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{clubId}/joinRequest/{requestId}/denyRequest")
    public ResponseEntity<?> denyRequest(
            @PathVariable("requestId") Long requestId,
            @PathVariable("clubId") Long clubId,
            @RequestParam("status") MemberStatus status) {
        // 동아리 회장인지 검사
        if (status!=MemberStatus.CLUB_PRESIDENT) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("동아리 회장만 접근 가능합니다"));
        }

        // 가입 거절
        joinRequestService.denyRequest(requestId);
        return ResponseEntity.ok("가입을 거절했습니다.");
    }
}
