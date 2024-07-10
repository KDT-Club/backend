package com.ac.su.community.club;


import com.ac.su.ResponseMessage;
import com.ac.su.clubmember.ClubMember;
import com.ac.su.clubmember.ClubMemberRepository;
import com.ac.su.member.CustonUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class ClubController {

    private final ClubRepository clubRepository; //Club 객체에 대한 입출력 함수
    private final ClubMemberRepository clubMemberRepository; //ClubMember 객체에 대한 입출력 함수
    private final ClubService clubService;
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

}
