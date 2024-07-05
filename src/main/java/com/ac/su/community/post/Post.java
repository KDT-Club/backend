package com.ac.su.community.post;

@Entity
@Getter
@Setter
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-incremnet
    @Column(name="post_id")
    private Long id;
    @Column
    private String title;
    @Column
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
    private Board boardId;  //게시판 고유 번호
}