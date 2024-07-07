package com.ac.su.joinrequest;

import com.ac.su.clubmember.*;
import com.ac.su.community.club.Club;
import com.ac.su.community.club.ClubRepository;
import com.ac.su.member.Member;
import com.ac.su.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;

    // 동아리 id로 동아리 지원서 전체 검색
    public List<JoinRequestDTO> findRequestByClubId(Long clubId) {
        List<JoinRequest> joinRequestList = joinRequestRepository.findByClubIdAndStatus(clubId, RequestStatus.WAITING);
        // 동아리 지원서가 한 개도 없을 때 빈 리스트 반환
        if(joinRequestList.isEmpty()){
            return new ArrayList<>();
        }
        // JoinRequest -> JoinRequestDTO로 변환
        List<JoinRequestDTO> joinRequestDTOList = new ArrayList<>();
        for (JoinRequest joinRequest : joinRequestList){
            joinRequestDTOList.add(JoinRequestDTO.toJoinRequestDTO(joinRequest));
        }
        return joinRequestDTOList;
    }

    // 지원서 id로 동아리 지원서 검색 후 상세 정보 출력
    public JoinRequestDTO findByRequestId(Long requestId) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId).orElseThrow();
        return JoinRequestDTO.toJoinRequestDTO(joinRequest);
    }

    // 가입 승인
    public void approveRequest(Long requestId, Long clubId, Long memberId) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId).orElseThrow();
        // 지원서 상태 WAITING -> APPROVED
        joinRequest.setStatus(RequestStatus.APPROVED);
        joinRequestRepository.save(joinRequest);

        Club club = clubRepository.findById(clubId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();
        ClubMember clubMember = new ClubMember();

        // 회원을 clubMember 테이블에 추가
        clubMember.setClub(club);
        clubMember.setMember(member);
        clubMember.setId(new ClubMemberId(clubId, memberId));
        clubMemberRepository.save(clubMember);
    }
}
