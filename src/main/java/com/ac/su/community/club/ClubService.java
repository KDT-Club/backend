package com.ac.su.community.club;

import com.ac.su.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    public List<Club> makeDummyClubData(int count) {
        List<Club> clubList = new ArrayList<>();
        // 멤버 10 개의 더미 데이터 생성 후 저장
        for (int i = 1; i <= count; i++) {
            Club club = new Club();
            club.setCategory("음악"+i);
            club.setClubImgUrl("static/path/to/image"+i);
            club.setClubSlogan("밴드 동아리"+i+"입니다!");
            club.setCreatedAt(LocalDateTime.now());
            club.setDescription("실력자들만 있는 밴드 동아리"+i);
            club.setName("스키마"+i);
            club.setMember(memberRepository.findById((long) i).get());
            clubRepository.save(club);
            clubList.add(club);
        }
        return clubRepository.saveAll(clubList);
    }
}
