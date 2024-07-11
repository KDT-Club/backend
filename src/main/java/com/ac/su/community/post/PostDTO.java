package com.ac.su.community.post;

import com.ac.su.community.attachment.AttachmentFlag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PostDTO {
    private String title;
    private String content;
    private List<MultipartFile> attachments;
    private AttachmentFlag attachmentFlag = AttachmentFlag.N;
    private String clubName;
    private List<String> attachmentUrls;

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

    public List<MultipartFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MultipartFile> attachments) {
        this.attachments = attachments;
    }

    public AttachmentFlag getAttachmentFlag() {
        return attachmentFlag;
    }

    public void setAttachmentFlag(AttachmentFlag attachmentFlag) {
        this.attachmentFlag = attachmentFlag;
    }


    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public List<String> getAttachmentUrls() {
        return attachmentUrls;
    }

    public void setAttachmentUrls(List<String> attachmentUrls) {
        this.attachmentUrls = attachmentUrls;
    }
}
