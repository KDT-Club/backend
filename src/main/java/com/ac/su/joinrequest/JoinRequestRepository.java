package com.ac.su.joinrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    // 동아리 id로 지원서 검색
    List<JoinRequest> findByClubId(Long clubId);
}
