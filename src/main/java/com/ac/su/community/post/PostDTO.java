package com.ac.su.community.post;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String attachmentFlag;
    private String attachmentName;

}
