package com.ac.su.notification;

@Entity
@Getter
@Setter
@ToString
public class Notification
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-incremnet
    @Column(name="notification_id")
    private Long id;
    private String message;
    @CreationTimestamp
    private LocalDateTime createdAt; // 생성 날짜
    @ManyToOne
    @JoinColumn(name="member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member; // 멤버 고유 번호
}
