package com.ac.su.member;

import com.ac.su.ResponseMessage;
import com.ac.su.clubmember.MemberStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository; //member 객체에 대한 입출력 함수

    //메인페이지로 이동
    @GetMapping("/")
    ResponseMessage test() {
        return new ResponseMessage("성공");
    }

}



