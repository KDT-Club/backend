package com.ac.su.comment;

@Entity
@Getter
@Setter
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-incremnet
    @Column(name="comment_id")
    private Long id;
    @Column
    private String content; //댓글 내용
    @CreationTimestamp
    @Column
    private LocalDateTime createdAt; // 생성 날짜
    @ManyToOne
    @JoinColumn(name="post_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Post postId; // 게시글 고유 번호
    @ManyToOne
    @JoinColumn(name="member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member; // 회원 고유번호
}