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
    private int studentId;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column
    private String memberImageURL;

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