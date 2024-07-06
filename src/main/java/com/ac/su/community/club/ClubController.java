package com.ac.su.community.club;

import com.ac.su.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ClubController {

    private final ClubRepository clubRepository; //Club 객체에 대한 입출력 함수

    // 설명: 모든 동아리 정보를 불러온다
    // /clubs
    @GetMapping("/clubs")
    public List<Club> getClubs() {

        var a = clubRepository.findAll(); // 모든 동아리 데이터 가져오기
        System.out.println(a);

        return a;
    }

    // 내 동아리 목록 (동아리 메뉴 초기 페이지)
    // /clubs?memberId={memberId}
    @GetMapping("/clubs/{clubName}")
    public Optional<Club> getClubByName(@PathVariable String clubName) {
        Optional<Club> a = clubRepository.findByName(clubName);

        return a;
    }

}
