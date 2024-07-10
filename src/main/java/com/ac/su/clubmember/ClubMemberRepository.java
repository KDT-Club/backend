package com.ac.su.clubmember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, ClubMemberId> {
    // 입력된 회원의 선호 카테고리를 검색
    List<UserFavoriteCategory> findByMember(Member member);
    // 입력된 회원의 선호 카테고리를 검색
    List<UserFavoriteCategory> findByMemberId(Long memberId);
}
