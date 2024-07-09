package com.ac.su.joinrequest;

import com.ac.su.ResponseMessage;
import com.ac.su.clubmember.RequestStatus;
import com.ac.su.community.club.Club;
import com.ac.su.community.club.ClubRepository;
import com.ac.su.member.CustonUser;
import com.ac.su.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JoinRequestService {

    private final ClubRepository clubRepository;
    private final JoinRequestRepository joinRequestRepository;

        public ResponseEntity<ResponseMessage> applyToClub(String clubName, ApplicationRequest request, Authentication auth) {
            // 인증되지 않은 사용자
            if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage("로그인 필요"));
            }
            // 동아리를 찾을 수 없는 경우
            Optional<Club> clubOptional = clubRepository.findByName(clubName);
            if (clubOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("현재 그런 동아리 없음"));
            }

            //JoinRequest컬럼에 club 필드를 위한 club 객체 생성
            var club = new Club();
            club.setId(clubOptional.get().getId()); //club 객체의 Id 필드를 현재 지원 중인 "클럽이름"으로 저장

            CustonUser user = (CustonUser) auth.getPrincipal();
            //JoinRequest컬럼에 member 필드를 위한 club 객체 생성
            var member = new Member();
            member.setId(user.getId()); //member 객체의 Id 필드를 현재 로그인 유저의 id로 저장

            // 새로운 가입 신청 생성
            JoinRequest joinRequest = new JoinRequest();
            joinRequest.setIntroduction(request.getMotivation());
            joinRequest.setStatus(RequestStatus.WAITING); // 지원 시에는 "지원상태"를 "WAITING"로 설정
            joinRequest.setMember(member);
            joinRequest.setClub(club);

            joinRequestRepository.save(joinRequest); // JoinRequest

            // 성공적인 응답
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("가입신청이 완료!"));
}
}
