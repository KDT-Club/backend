package com.ac.su.member;

import com.ac.su.clubmember.ClubMember;
import com.ac.su.community.club.Club;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

    @Column
    private String name;

    @Column
    private String department;

    @Column
    private String studentId;

    @Column
    private String password;

    @Column
    private String memberImageURL;

    @Column
    private String phone;

    @OneToMany(mappedBy = "member")
    private List<ClubMember> joinedClubs; // 가입된 클럽 목록

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Club club; // 동아리장이 관리하는 동아리

//    @JsonIgnore
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Club> clubs = new ArrayList<>();


    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", studentId=" + studentId +
                ", password='" + password + '\'' +
                ", phone=" + phone +
                ", memberImageURL='" + memberImageURL + '\'' +
                '}';
    }
}
