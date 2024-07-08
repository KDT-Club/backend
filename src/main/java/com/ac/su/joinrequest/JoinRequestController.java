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

    private final JoinRequestService joinRequestService;
@PostMapping("/clubs/{clubName}/applications")
public ResponseEntity<?> applyToClub(@PathVariable String clubName,
                                     @RequestBody ApplicationRequest request,
                                     Authentication auth) {
    //가입 신청 로직 서비스 레이어로 분리함
    ResponseEntity message = joinRequestService.applyToClub(clubName, request, auth);
    return message;
}

}
@Getter
@Setter
class ApplicationRequest {
    private String clubName;
    private String motivation;
}

