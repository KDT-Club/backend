package com.ac.su.community.board;

import lombok.Getter;

import java.time.LocalDateTime;

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

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
