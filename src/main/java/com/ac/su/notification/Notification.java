package com.ac.su.notification;

import com.ac.su.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
//import org.springframework.data.annotation.Id;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment
    @Column(name="notification_id")
    private Long id;
    private String message;

    @CreationTimestamp
    private LocalDateTime createdAt; // 생성 날짜

    @ManyToOne
    @JoinColumn(name="member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member; // 멤버 고유 번호
}
