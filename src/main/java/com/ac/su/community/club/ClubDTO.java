package com.ac.su.community.club;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClubDTO {
    private Long clubId;
    private String clubName;
    private String description;
    private String category;
    private Long member;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private String clubImgUrl;
    private String clubSlogan;

    // 동아리 수정 기능에서 표시될 내용입니다
    // (일부러 동아리 이름, 동아리 슬로건, 동아리 설명, 동아리 이미지만 표시되게 만들었어요!!)
    public static ClubDTO toClubDTO(Club club){
        ClubDTO clubDTO = new ClubDTO();
        clubDTO.setClubName(club.getName());
        clubDTO.setClubSlogan(club.getClubSlogan());
        clubDTO.setDescription(club.getDescription());
        clubDTO.setClubImgUrl(club.getClubImgUrl());

        return clubDTO;
    }
}
