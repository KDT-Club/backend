package com.ac.su.community.club;


import com.ac.su.ResponseMessage;
import com.ac.su.clubmember.*;
import com.ac.su.member.CustonUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class ClubController {

    private final ClubRepository clubRepository; //Club 객체에 대한 입출력 함수
    private final ClubMemberRepository clubMemberRepository; //ClubMember 객체에 대한 입출력 함수
    private final ClubService clubService;
    private final ClubMemberService clubMemberService;
    // 설명: 모든 동아리 정보를 불러온다
    // /clubs
//    @GetMapping("/clubs")
//    public List<Club> getClubs(@RequestParam(name = "memberId", required = false) String memberId) {
//
//        if("2020101460".equals(memberId)) { // /clubs?memberId={memberId}
//            List<Club> b = new ArrayList<>();
//            System.out.println("if문 실행됨");
//            return b;
//        }
//        else {
//            var a = clubRepository.findAll(); // 모든 동아리 데이터 가져오기
//            System.out.println(a);
//            System.out.println("else문 실행됨");
//            return a;
//        }
//    }

    @GetMapping("/clubs")
    public ResponseEntity<?> getClubs(@RequestParam(name = "memberId", required = false) Long memberId) {
        if (memberId == null) {
            // memberId가 없을 때 /clubs
            return clubService.getAllClubs();
        } else {
            // memberId가 있을 때 /clubs?memberId=2
            return clubService.getClubsByMemberId(memberId);
        }
    }

//    public ResponseEntity<?> getAllClubs() {
//        List<Club> clubs = clubRepository.findAll(); // 모든 동아리 데이터 가져오기
//        return ResponseEntity.ok(clubs);
//    }
//
//    /// clubs?memberId={memberId}
//    public ResponseEntity<?> getClubsByMemberId(Long memberId) {
//        // 특정 memberId로 클럽을 조회하는 로직
//        Optional<ClubMember> clubs = clubMemberRepository.findByMemberId(memberId);
//        if (clubs.isEmpty()) {
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "가입한 동아리 없음");
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.ok(clubs);
//        }
//    }

    // 내 동아리 목록 (동아리 메뉴 초기 페이지)
    // /clubs?memberId={memberId}
    @GetMapping("/clubs/{clubName}")
    public Optional<Club> getClubByName(@PathVariable String clubName) {
        Optional<Club> a = clubRepository.findByName(clubName); //간단해서 서비스 레이어로 분리안했음
        return a;
    }

    // 동아리 정보 불러옴(GET)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/clubs/{clubId}/changeClubInfo")
    public ResponseEntity<?> getClubInfo(@PathVariable("clubId") Long clubId,
                                         Authentication auth) {
        // 회원 상태 가져오기
        CustonUser user = (CustonUser) auth.getPrincipal();
        MemberStatus status = clubMemberService.getMemberStatus(new ClubMemberId(user.getId(), clubId));

        // 동아리 회장이 아닌 경우 접근 금지
        if (status != MemberStatus.CLUB_PRESIDENT) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage("동아리 회장만 접근 가능합니다"));
        }

        // 동아리 정보 불러옴
        ClubDTO clubDTO = clubService.getClubByClubId(clubId);
        return ResponseEntity.ok(clubDTO);
    }

    // 동아리 상태 수정(POST)
    @PostAuthorize("isAuthenticated()")
    @PostMapping("/clubs/{clubId}/changeClubInfo")
    public ResponseEntity<?> changeStatus(@PathVariable("clubId") Long clubId,
                                          @RequestParam("clubName") String clubName,
                                          @RequestParam("clubSlogan") String clubSlogan,
                                          @RequestParam("description") String description) {
        // 현재 저장되어 있는 동아리 정보 받아옴
        ClubDTO existingClubInfo = clubService.getClubByClubId(clubId);

        // 사용자가 수정한 값이 없는 경우 기존 정보 그대로 반영
        if (clubName.isEmpty()) {
            clubName = existingClubInfo.getClubName();
        }
        if (clubSlogan.isEmpty()) {
            clubSlogan = existingClubInfo.getClubSlogan();
        }
        if (description.isEmpty()) {
            description = existingClubInfo.getDescription();
        }

        // 동아리 정보 변경
        clubService.changeClubInfo(clubId, clubName, clubSlogan, description);
        return ResponseEntity.ok(new ResponseMessage("동아리 정보가 성공적으로 변경되었습니다."));
    }

}
