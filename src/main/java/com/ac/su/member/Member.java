package com.ac.su.member;

import com.ac.su.clubmember.ClubMember;
import com.ac.su.clubmember.MemberStatus;
import com.ac.su.community.club.Club;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String department;

    @Column(unique = true, nullable = false, name = "student_id")
    private int studentId;

    @Column(length = 20, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(nullable = false)
    private MemberStatus status;

    @Column(name = "member_imageurl")
    private String memberImageURL;

    @OneToOne(mappedBy = "member")
    private Club managedClub; // 동아리장

    @OneToMany(mappedBy = "member")
    private List<ClubMember> joinedClubs; // 가입된 클럽 목록

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", studentId=" + studentId +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", memberImageURL='" + memberImageURL + '\'' +
                '}';
    }
}
