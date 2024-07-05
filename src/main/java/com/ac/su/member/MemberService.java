package com.ac.su.member;

import com.ac.su.clubmember.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    // DB 접근
    private final MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(long memberId) {
        return memberRepository.findById(memberId);
    }

    public List<Member> makeDummyData(int count) {
        List<Member> memberList = new ArrayList<>();
        // 멤버 10 개의 더미 데이터 생성 후 저장
        for (int i = 1; i <= count; i++) {
            Member member = new Member();
            member.setName("이다민 " + i);
            member.setDepartment("컴퓨터공학부" + i);
            member.setStudentId(2021100148 + i);
            member.setPassword("password"+i);
            member.setStatus(MemberStatus.MEMBER);
            member.setMemberImageURL("static/path/to/image"+i);
            memberRepository.save(member);
            memberList.add(member);
        }
        return memberRepository.saveAll(memberList);
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }


    public Member updateMemberPut(long id, Member member) {
        // 존재하지 않는 레코드 id인 경우 null 반환
        if (!memberRepository.existsById(id)) {
            return null;
        }
        // 기존에 존재하는 레코드 업데이트
        member.setId(id);
        return memberRepository.save(member);
    }

    public Member updateMemberPatch(long id, Member member) {
        // 존재하지 않는 레코드 id인 경우 null 반환
        if (!memberRepository.existsById(id)) {
            return null;
        }
        // 기존에 존재하는 레코드 업데이트
        if(memberRepository.findById(id).isPresent()) {
            Member recordToPatch = memberRepository.findById(id).get();
            if(member.getPassword() != null) {
                recordToPatch.setPassword(member.getPassword());
            }
            if(member.getStatus() != null) {
                recordToPatch.setStatus(member.getStatus());
            }
            if(member.getMemberImageURL() != null) {
                recordToPatch.setMemberImageURL(member.getMemberImageURL());
            }
        }
        return memberRepository.save(member);
    }

    public boolean deleteMember(long id) {
        if(memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
