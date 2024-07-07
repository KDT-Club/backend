package com.ac.su.member;

import com.ac.su.clubmember.MemberStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public MemberDTO getMemberById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.map(this::convertToDTO).orElse(null);
    }

    private MemberDTO convertToDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setName(member.getName());
        memberDTO.setDepartment(member.getDepartment());
        memberDTO.setStudentId(member.getStudentId());
        memberDTO.setStatus(String.valueOf(member.getStatus()));
        memberDTO.setMemberImageURL(member.getMemberImageURL());
        return memberDTO;
    }
}
