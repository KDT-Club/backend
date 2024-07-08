package com.ac.su;

import com.ac.su.clubmember.MemberStatus;
import com.ac.su.member.Member;
import com.ac.su.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository; //member 객체에 대한 입출력 함수
    private final PasswordEncoder passwordEncoder;

    //회원가입 기능 - /signup
    @PostMapping("/signup")
    ResponseMessage signup(
            String name,
            String username,
            String password,
            String department) {
        //유저가 보낸 학번, 학과, 이름 등을 저장
        Member member = new Member();
        member.setName(name);  //학생 이름 name
        member.setStudentId(username); //학번 프론트에서 username으로 보내줘여함!! -> 스프링 시큐리티 username
        var hashed_password = passwordEncoder.encode(password);
        member.setPassword(hashed_password); //비밀번호 해싱해서 저장
        member.setDepartment(department); // 학과
        member.setStatus(MemberStatus.MEMBER); //멤버 상태: 초기 회원가입 시에는 Member로 고정
        memberRepository.save(member); //DB에 유저 정보 저장

        return new ResponseMessage("성공"); //로그인 페이지로 이동 리다이렉트
    }

    //로그인 페이지로 이동
    @GetMapping("/login")
    String login() {
//        var a = memberRepository.findByStudentId("2020101460");
//        System.out.println(a.get());
//        var hashed_password = passwordEncoder.encode("1234");
//        System.out.println(hashed_password);
        return "login";
    }
    @GetMapping("/mainPage")
    String mainpage(Authentication auth) {
        System.out.println(auth.getPrincipal());
        System.out.println(auth.getName());
        return "mainpage";
    }

}
