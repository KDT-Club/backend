package com.ac.su.community.post;

import com.ac.su.community.attachment.AttachmentFlag;
import com.ac.su.community.board.Board;
import com.ac.su.member.Member;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment
    @Column(name="post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt; // 생성 날짜

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt; // 수정 날짜

    @Column
    @Enumerated(EnumType.STRING)
    private AttachmentFlag attachmentFlag; // Enum 타입으로 변경

    @Column
    private String postType;

    @Column
    private String clubName;

    @ManyToOne
    @JoinColumn(name="member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member; // 회원 고유번호

    @ManyToOne
    @JoinColumn(name="board_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Board board;  //게시판 고유 번호

    @Column(name = "attachment_name", nullable = true)
    private String attachmentName;

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }
}
