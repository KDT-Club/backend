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

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String department;

    @Column(unique = true, nullable = false)
    private int studentId;

    @Column(length = 20, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(nullable = false)
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