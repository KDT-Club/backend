package com.ac.su.clubmember;

@Entity
@Getter
@Setter
@Table(name = "ClubMember")
public class ClubMember {
    @EmbeddedId
    private ClubMemberId id;

    @MapsId("memberId")
    @ManyToOne
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @MapsId("clubId")
    @ManyToOne
    @JoinColumn(name = "club_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Club club;

    @Override
    public String toString() {
        return "ClubMember{" +
                "id=" + id +
                ", member=" + member +
                ", club=" + club +
                '}';
    }
}