package com.ac.su.clubmember;

import com.ac.su.community.club.ClubRepository;
import com.ac.su.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubMemberService {
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;

    public List<ClubMemberDTO> findAll() {
        List<ClubMember> clubMemberList = clubMemberRepository.findAll();
        //ClubMember -> ClubMemberDTO로 변환
        List<ClubMemberDTO> clubMemberDTOList = new ArrayList<>();
        for (ClubMember clubMember : clubMemberList){
            clubMemberDTOList.add(ClubMemberDTO.toClubMemberDTO(clubMember));
        }
        return clubMemberDTOList;
    }

//    @Transactional
//    public List<ClubMember> makeDummyClubMemberData(int count) {
//        List<ClubMember> clubMemberList = new ArrayList<>();
//        Club club = clubRepository.findById(31L).orElseThrow();
//
//        for (int i = 1; i <= count; i++) {
//            Long memberId = (long) i;
//            ClubMemberId clubMemberId = new ClubMemberId(31L, memberId);
//
//            if (clubMemberRepository.existsById(clubMemberId)) {
//                continue; // Skip if the club member already exists
//            }
//
//            ClubMember clubMember = new ClubMember();
//            clubMember.setClub(club);
//            clubMember.setMember(memberRepository.findById(memberId).orElseThrow());
//            clubMember.setId(clubMemberId);
//            clubMemberList.add(clubMember);
//        }
//
//        return clubMemberRepository.saveAll(clubMemberList);
//    }
}
