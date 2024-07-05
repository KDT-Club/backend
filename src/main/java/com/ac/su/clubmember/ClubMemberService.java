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

    // 동아리 id로 동아리 회원 전체 검색
    public List<ClubMemberDTO> findAllByClubId(Long clubId) {
        List<ClubMember> clubMemberList = clubMemberRepository.findByClubId(clubId);
        // ClubMember -> ClubMemberDTO로 변환
        List<ClubMemberDTO> clubMemberDTOList = new ArrayList<>();
        for (ClubMember clubMember : clubMemberList){
            clubMemberDTOList.add(ClubMemberDTO.toClubMemberDTO(clubMember));
        }
        return clubMemberDTOList;
    }

    // 멤버 id로 멤버 검색
    public ClubMemberDTO findById(Long memberId, Long clubId) {
        ClubMember clubMember = clubMemberRepository.findById(new ClubMemberId(memberId, clubId)).orElseThrow(() -> new IllegalArgumentException("동아리에 가입되지 않은 회원입니다."));
        return ClubMemberDTO.toClubMemberDTO(clubMember);
    }

    // 회원 상태 수정
    public void changeStatus(Long memberId, Long clubId, MemberStatus status) {
        ClubMember clubMember = clubMemberRepository.findById(new ClubMemberId(memberId, clubId)).orElseThrow(() -> new IllegalArgumentException("동아리에 가입되지 않은 회원입니다."));
        clubMember.getMember().setStatus(status);
        memberRepository.save(clubMember.getMember());
    }

    public void deleteMember(Long memberId, Long clubId) {
        clubMemberRepository.deleteById(new ClubMemberId(memberId, clubId));
    }
}
