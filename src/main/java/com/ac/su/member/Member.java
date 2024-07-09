package com.ac.su.member;


import com.ac.su.clubmember.MemberStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    //MemberStatus 컬럼 ClubMember로 옮기는 것로 수정함
//    @Column
//    @Enumerated(EnumType.STRING)
//    private MemberStatus status;

    @Column
    private String memberImageURL;

}