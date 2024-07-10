package com.ac.su.community.club;

import com.ac.su.clubmember.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class ClubController {

    private final ClubRepository clubRepository; //Club 객체에 대한 입출력 함수
    private final ClubMemberRepository clubMemberRepository; //ClubMember 객체에 대한 입출력 함수
    private final ClubService clubService;

    // 설명: 모든 동아리 정보를 불러온다
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

    // 내 동아리 목록 (동아리 메뉴 초기 페이지)
    // /clubs?memberId={memberId}
    @GetMapping("/clubs/{clubName}")
    public Optional<Club> getClubByName(@PathVariable String clubName) {
        Optional<Club> a = clubRepository.findByName(clubName); //간단해서 서비스 레이어로 분리안했음
        return a;
    }

    // 동아리 생성
    @PostMapping("/clubs/{memberId}")
        public ResponseEntity<?> createClub(@PathVariable Long memberId, @RequestBody ClubDTO clubDTO) {
        Club createdClub = clubService.createClub(clubDTO,memberId);
        if (createdClub == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("클럽 생성 실패");
        } else {
            return  ResponseEntity.status(HttpStatus.OK).body("클럽 생성 성공");
        }
      }
    }

