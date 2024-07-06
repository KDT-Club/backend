package com.ac.su.joinrequest;

import com.ac.su.clubmember.RequestStatus;
import com.ac.su.community.club.Club;
import com.ac.su.community.club.ClubRepository;
import com.ac.su.member.CustonUser;
import com.ac.su.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class JoinRequestController {

    private final ClubRepository clubRepository;
    private final JoinRequestRepository joinRequestRepository;

    @PostMapping("/clubs/{clubName}/applications")
    public ResponseEntity<?> applyToClub(@PathVariable String clubName,
                                         @RequestBody ApplicationRequest request,
                                         Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("가입 신청하려면 로그인 해야함");
        }

        Optional<Club> clubOptional = clubRepository.findByName(clubName);
        if (clubOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(clubName + "현재 그런 동아리 없음");
        }

        //JoinRequest컬럼에 club 필드를 위한 club 객체 생성
        var club = new Club();
        club.setId(clubOptional.get().getId()); //club 객체의 Id 필드를 현재 지원 중인 "클럽이름"으로 저장

        CustonUser user = (CustonUser) auth.getPrincipal();
        //JoinRequest컬럼에 member 필드를 위한 club 객체 생성
        var member = new Member();
        member.setId(user.getId()); //member 객체의 Id 필드를 현재 로그인 유저의 id로 저장

        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setIntroduction(request.getMotivation());
        joinRequest.setStatus(RequestStatus.WAITING); // 지원 시에는 "지원상태"를 "WAITING"로 설정
        joinRequest.setMember(member);
        joinRequest.setClub(club);

        joinRequestRepository.save(joinRequest); // JoinRequest

        return ResponseEntity.status(HttpStatus.CREATED).body("가입신청이 성공적으로 완료됨!!!");
    }

}
@Getter
@Setter
class ApplicationRequest {
    private String clubName;
    private String motivation;
}

