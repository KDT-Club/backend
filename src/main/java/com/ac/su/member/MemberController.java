package com.ac.su.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 멤버 불러오기
    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMember(@RequestParam Long id) {
        Optional<Member> memberOptional = memberService.getMemberById(id);
        if (memberOptional.isPresent()) {
            return ResponseEntity.ok(memberOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // 멤버 정보 수정
    @PostMapping("/{memberId}")
    // id랑 dto 값 받아서 저장 - 부분 수정이라도 전체 값을 받아야 함.
    public ResponseEntity<Member> updateMember(@RequestParam Long id, @RequestBody MemberDTO memberDTO) {
        Member updatedMember = memberService.updateMember(id, memberDTO);
        if (updatedMember != null) {
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // 멤버 삭제
    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> deleteMember(@RequestParam Long id) {
        Optional<Member> memberOptional = memberService.getMemberById(id);
        if (memberOptional.isPresent()) {
            memberService.deleteMember(id);
            return ResponseEntity.ok("계정 삭제 성공!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("계정 삭제 실패!");
        }
    }

}

