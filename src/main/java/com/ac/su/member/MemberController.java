package com.ac.su.member;

import com.ac.su.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    //메인페이지로 이동
    @GetMapping("/")
    ResponseMessage test() {
        return new ResponseMessage("성공");
    }
}
