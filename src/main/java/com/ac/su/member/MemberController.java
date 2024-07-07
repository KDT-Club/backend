package com.ac.su.member;

import com.ac.su.member.MemberDTO;
import com.ac.su.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/member")
    public MemberDTO getMemberById(@RequestParam Long id) {
        return (MemberDTO) memberService.getMemberById(id);
    }
}
