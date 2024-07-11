package com.ac.su.community.board;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BoardDTO {
    // Getters and setters
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long memberId;

    // Constructor
    public BoardDTO(Long postId, String title, String content, LocalDateTime createdAt, Long memberId) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.memberId = memberId;
    }

}
