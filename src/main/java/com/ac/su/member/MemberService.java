package com.ac.su.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Optional<Member> getMemberById(Long memberId) {

        return memberRepository.findById(memberId);
    }

    public Member updateMember(Long memberId, MemberDTO memberDTO) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        //멤버 존재 여부 확인 및 수정
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            member.setName(memberDTO.getName());
            member.setDepartment(memberDTO.getDepartment());
            member.setStudentId(memberDTO.getStudentId());
            member.setPassword(memberDTO.getPassword());
            member.setMemberImageURL(memberDTO.getMemberImageURL());
            member.setPhone(memberDTO.getPhone());
            //DB에 저장
            return memberRepository.save(member);
        }
        return null;
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}