package com.ac.su.community.attachment;

import com.ac.su.community.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    @Column(name = "attachment_id")
    private Long id;

    private String attachmentName;

    @CreationTimestamp
    private LocalDateTime createdAt;    // 생성 날짜

    @ManyToOne
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Post postId;    // 게시글 고유 번호 (수정된 부분: 필드 이름을 postId에서 post로 변경)


}
