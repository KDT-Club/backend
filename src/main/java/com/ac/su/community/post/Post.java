package com.ac.su.community.post;

import com.ac.su.community.attachment.Attachment;
import com.ac.su.community.attachment.AttachmentFlag;
import com.ac.su.community.board.Board;
import com.ac.su.member.Member;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.List;

@Entity
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

    @ManyToOne
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;  // 회원 고유번호

    @ManyToOne
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Board board;    //게시판 고유 번호

    @Column
    @Enumerated(EnumType.STRING)
    private AttachmentFlag attachmentFlag; // Enum 타입으로 변경

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments; // 첨부파일 목록


    @Column
    private String clubName;




    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
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

}
