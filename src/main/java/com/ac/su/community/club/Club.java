package com.ac.su.community.club;

import com.ac.su.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Club")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="club_id")
    private Long id;

    @Column(length=20, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private CategoryStatus Category;

    @CreationTimestamp
    private LocalDateTime createdAt; // 생성 날짜

    @Column
    private String clubImgUrl;

    @Column
    private String clubSlogan;

    // 동아리 회장 참조키
    @ManyToOne
    @JoinColumn(name="member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", clubImgUrl='" + clubImgUrl + '\'' +
                ", clubSlogan='" + clubSlogan + '\'' +
                ", member=" + member +
                '}';
    }
}