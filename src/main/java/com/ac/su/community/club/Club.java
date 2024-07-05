package com.ac.su.community.club;

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

    @Column(length=20, nullable = false)
    private String category;

    @Column
    private Date createdAt;

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
                ", category='" + category + '\'' +
                ", createdAt=" + createdAt +
                ", clubImgUrl='" + clubImgUrl + '\'' +
                ", clubSlogan='" + clubSlogan + '\'' +
                ", member=" + member +
                '}';
    }
}