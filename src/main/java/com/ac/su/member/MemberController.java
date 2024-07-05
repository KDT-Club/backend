package com.ac.su.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> member = memberService.getAllMembers();
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable("id") long id) {
        Optional<Member> member = memberService.getMemberById(id);
        return member.map(
                value -> new ResponseEntity<>(value, HttpStatus.OK)
        ).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping
    public ResponseEntity<Member> createMember(
            // Member 객체를 수신 (JSON 타입 -> Member 객체로 자동 변환)
            @RequestBody Member member
    ) {
        // Service 에 직접적인 로직 위탁
        Member createdMember = memberService.createMember(member);
        // 생성된 Member 객체를 반환 (JSON 타입으로 자동 변환)
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMemberPut(
            // Put할 멤버 데이터 받아옴
            @PathVariable("id") long id,
            @RequestBody Member member
    ) {
        Member updatedMember = memberService.updateMemberPut(id, member);
        // Upsert
        // 변경할 멤버 데이터가 존재할 때
        if (updatedMember != null) {
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        }
        // 변경할 멤버 데이터가 존재하지 않을 때
        Member createdMember = memberService.createMember(member);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") long id) {
        boolean isDeleted = memberService.deleteMember(id);
        if (isDeleted) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/make-dummy")
    public ResponseEntity<List<Member>> makeDummyData(@RequestParam("count") int count) {
        // count 값이 1 이상 100 이하가 되도록 제약조건 추가하기!
        if(count<1 || count>100) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
        List<Member> members = memberService.makeDummyData(count);
        return new ResponseEntity<>(members, HttpStatus.OK);
    }
}
