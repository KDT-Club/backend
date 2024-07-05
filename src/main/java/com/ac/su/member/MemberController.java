package com.ac.su.member;

import com.ac.su.ResponseMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    //메인페이지로 이동
    @GetMapping("/")
    ResponseMessage test() {
        return new ResponseMessage("성공");
    }
    //회원가입 기능 - /signup
    @PostMapping("/signup")
    String signup() {
        return "회원가입 성공"; //로그인 페이지로 이동 리다이렉트
    }
}



