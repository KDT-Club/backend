package com.ac.su.community.club;

import com.ac.su.clubmember.ClubMember;
import com.ac.su.clubmember.ClubMemberId;
import com.ac.su.clubmember.ClubMemberRepository;
import com.ac.su.clubmember.MemberStatus;
import com.ac.su.joinrequest.JoinRequestRepository;
import com.ac.su.member.Member;
import com.ac.su.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성해주는 Lombok 어노테이션
public class ClubService {

    private final MemberRepository memberRepository; // Member 객체에 대한 데이터베이스 입출력 함수
    private final ClubRepository clubRepository; // Club 객체에 대한 데이터베이스 입출력 함수
    private final ClubMemberRepository clubMemberRepository; // ClubMember 객체에 대한 데이터베이스 입출력 함수
    private final JoinRequestRepository joinRequestRepository;

    /**
     * 모든 클럽 데이터를 가져와서 클라이언트에게 반환합니다.
     *
     * @return 모든 클럽 데이터를 담은 ResponseEntity 객체
     */
    public ResponseEntity<?> getAllClubs() {
        List<Club> clubs = clubRepository.findAll(); // 모든 클럽 데이터를 데이터베이스에서 조회
        // 클럽 데이터를 ClubDTO로 변환
        List<ClubDTO> responseDTOs = clubs.stream()
                .map(club -> new ClubDTO(
                        club.getId(),
                        club.getName(),
                        club.getDescription(),
                        club.getCategory(),
                        club.getMember().getId(), // 클럽 회원의 ID
                        club.getCreatedAt(),
                        club.getClubImgUrl(),
                        club.getClubSlogan()
                ))
                .collect(Collectors.toList()); // List<ClubDTO>로 변환
        return ResponseEntity.ok(responseDTOs); // 변환된 DTO 리스트를 응답으로 반환
    }

    /**
     * 특정 회원이 가입한 클럽 데이터를 가져와서 클라이언트에게 반환한다.
     *
     * @param memberId 회원의 ID
     * @return 회원이 가입한 클럽 데이터를 담은 ResponseEntity 객체
     */
    public ResponseEntity<?> getClubsByMemberId(Long memberId) {
        // 특정 memberId로 클럽 멤버 데이터를 조회
        Optional<ClubMember> clubMemberOptional = clubMemberRepository.findByMemberId(memberId);
        if (clubMemberOptional.isEmpty()) { // 클럽 멤버 데이터가 없으면
            Map<String, String> response = new HashMap<>();
            response.put("message", "가입한 동아리 없음"); // 가입한 동아리가 없음을 알리는 메시지를 반환
            return ResponseEntity.ok(response);
        } else { // 클럽 멤버 데이터가 있으면
            ClubMember clubMember = clubMemberOptional.get(); // 클럽 멤버 데이터 가져오기
            Club club = clubMember.getClub(); // 클럽 데이터 가져오기
            // 클럽 데이터를 ClubDTO로 변환
            ClubDTO responseDTO = new ClubDTO(
                    club.getId(),
                    club.getName(),
                    club.getDescription(),
                    club.getCategory(),
                    clubMember.getMember().getId(),
                    club.getCreatedAt(),
                    club.getClubImgUrl(),
                    club.getClubSlogan()
            );
            return ResponseEntity.ok(responseDTO); // 변환된 DTO를 응답으로 반환
        }

    }
    // 클럽 생성
    public Club createClub(ClubDTO request,Long memberId) {

        // 클럽 생성 요청을 받아서 클럽 객체를 생성
        Club club = new Club();
        club.setName(request.getClubName());
        club.setDescription(request.getDescription());
        club.setCategory(request.getCategory());
        club.setClubImgUrl(request.getClubImgUrl());
        club.setClubSlogan(request.getClubSlogan());

        Member member = memberRepository.findById(memberId).orElseThrow(()
                -> new RuntimeException("멤버 찾을수 없음"));
        club.setMember(member);// 클럽 생성 시 클럽 회장 지정

        // 클럽 데이터를 데이터베이스에 저장하고 저장된 객체를 받아옴
        Club savedClub = clubRepository.save(club);

        // ClubMemberId 생성
        ClubMemberId clubMemberId = new ClubMemberId();
        clubMemberId.setClubId(savedClub.getId()); // 클럽 ID 저장된 clubId 가져옴
        clubMemberId.setMemberId(memberId); // 멤버 ID 저장된 memberId 가져옴

        // ClubMember 엔티티 생성 및 설정
        ClubMember clubMember = new ClubMember();
        clubMember.setId(clubMemberId);
        clubMember.setClub(savedClub);
        clubMember.setMember(member);
        clubMember.setStatus(MemberStatus.CLUB_PRESIDENT); // 멤버 상태 클럽 회장으로 설정

        clubMemberRepository.save(clubMember); // ClubMember 데이터를 데이터베이스에 저장

        return savedClub;

    }

    // 특정 clubId로 클럽 데이터를 조회
    public ClubDTO getClubByClubId(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 동아리입니다."));
        return ClubDTO.toClubDTO(club);
    }

    // 동아리 정보 수정
    public void changeClubInfo(Long clubId, String clubName, String clubSlogan, String description, String clubImgUrl) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new IllegalArgumentException("동아리에 가입되지 않은 회원입니다."));
        club.setName(clubName);
        club.setClubSlogan(clubSlogan);
        club.setDescription(description);
        club.setClubImgUrl(clubImgUrl);

        clubRepository.save(club);
    }

    // 동아리 삭제
    public void deleteClub(Long clubId) {
        // @OnDelete(action = OnDeleteAction.CASCADE)가 안먹혀서 강경 수단 취함..
        // clubId를 참조하고 있는 clubMember 삭제
        clubMemberRepository.deleteByClubId(clubId);
        clubMemberRepository.flush();
        // clubId를 참조하고 있는 joinRequest 삭제
        joinRequestRepository.deleteByClubId(clubId);
        joinRequestRepository.flush();
        // clubId를 참조하고 있는 club 삭제
        clubRepository.deleteById(clubId);
        clubRepository.flush();
    }
}

