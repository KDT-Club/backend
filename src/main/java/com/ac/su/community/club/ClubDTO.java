package com.ac.su.community.club;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ClubDTO {
    private Long clubId;
    private String clubName;
    private String description;
    private String category;
    private Long memberId;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private String clubImgUrl;
}
