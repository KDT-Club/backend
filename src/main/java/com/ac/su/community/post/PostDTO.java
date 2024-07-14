package com.ac.su.community.post;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PostDTO {
    private String title;
    private String content;
    private String attachmentFlag; // "Y" 또는 "N"으로 설정
    private String clubName;
    private List<MultipartFile> attachmentNames; // 첨부 파일 리스트

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachmentFlag() {
        return attachmentFlag;
    }

    public void setAttachmentFlag(String attachmentFlag) {
        this.attachmentFlag = attachmentFlag;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public List<MultipartFile> getAttachmentNames() {
        return attachmentNames;
    }

    public void setAttachmentNames(List<MultipartFile> attachmentNames) {
        this.attachmentNames = attachmentNames;
    }
}
