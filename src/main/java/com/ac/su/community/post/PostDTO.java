package com.ac.su.community.post;

import com.ac.su.community.attachment.AttachmentFlag;
import org.springframework.web.multipart.MultipartFile;

public class PostDTO {
    private String title;
    private String content;
    private MultipartFile attachment;
    private AttachmentFlag attachmentFlag = AttachmentFlag.N;
    private String postType;

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

    public MultipartFile getAttachment() {
        return attachment;
    }

    public void setAttachment(MultipartFile attachment) {
        this.attachment = attachment;
    }

    public AttachmentFlag getAttachmentFlag() {
        return attachmentFlag;
    }

    public void setAttachmentFlag(AttachmentFlag attachmentFlag) {
        this.attachmentFlag = attachmentFlag;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }
}
