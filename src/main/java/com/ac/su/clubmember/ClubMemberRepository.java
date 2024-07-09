package com.ac.su.clubmember;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, ClubMemberId> {
    Optional<ClubMember> findByMemberId(Long memberId);
//     findByMember(Long memberId);
}
