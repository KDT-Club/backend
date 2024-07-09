package com.ac.su.member;

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

    @Column
    private String memberImageURL;

    @Column
    private String phone;

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