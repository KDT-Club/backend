package com.ac.su.community.club;

import com.ac.su.clubmember.ClubMember;
import com.ac.su.clubmember.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성해주는 Lombok 어노테이션
public class ClubService {

    private final ClubRepository clubRepository; // Club 객체에 대한 데이터베이스 입출력 함수
    private final ClubMemberRepository clubMemberRepository; // ClubMember 객체에 대한 데이터베이스 입출력 함수

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
                        club.getClubImgUrl()
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
                    club.getClubImgUrl()
            );
            return ResponseEntity.ok(responseDTO); // 변환된 DTO를 응답으로 반환
        }
    }
}
