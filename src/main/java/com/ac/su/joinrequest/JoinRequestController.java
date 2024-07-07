package com.ac.su.joinrequest;

import com.ac.su.clubmember.ClubMemberDTO;
import com.ac.su.clubmember.MemberStatus;
import com.ac.su.clubmember.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class JoinRequestController {
    private final JoinRequestService joinRequestService;

    // 동아리 id에 따른 동아리 지원서 리스트 출력
    @GetMapping("/{clubId}/joinRequest")
    public String joinRequestList(@PathVariable("clubId") Long requestId,
                                 Model model) {
        List<JoinRequestDTO> joinRequestDTOList = joinRequestService.findRequestByClubId(requestId);
        model.addAttribute("joinRequestList", joinRequestDTOList);
        return "join_request_list";
    }

    // 동아리 지원서 상세 정보
    @GetMapping("/{clubId}/joinRequest/{requestId}")
    public String requestDetail(@PathVariable("requestId") Long requestId,
                               Model model) {
        JoinRequestDTO joinRequestDTO = joinRequestService.findByRequestId(requestId);
        model.addAttribute("joinRequest", joinRequestDTO);
        return "join_request_detail";
    }

    // 가입 승인
    @PostMapping("/{clubId}/joinRequest/{requestId}/approveRequest")
    public String approveRequest(@PathVariable("requestId") Long requestId,
                                 @PathVariable("clubId") Long clubId,
                                 @RequestParam("memberId") Long memberId) {
        joinRequestService.approveRequest(requestId, clubId, memberId);
        // 가입 승인 후 동아리 지원서 리스트로 리다이렉트
        return "redirect:/clubs/" + clubId + "/joinRequest";
    }
}
