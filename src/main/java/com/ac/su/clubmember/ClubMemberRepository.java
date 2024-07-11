package com.ac.su.clubmember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, ClubMemberId> {
    Optional<ClubMember> findByMemberId(Long memberId);
//findByMember(Long memberId);
    //     findByMember(Long memberId);
    List<ClubMember> findByClubId(Long clubId);
    boolean existsById(ClubMemberId clubMemberId);

    // 특정 등급의 동아리 회원을 검색하는 메소드
    ClubMember findByClubIdAndStatus(Long clubId, MemberStatus status);
}
