package com.ac.su.member;

import com.ac.su.clubmember.MemberStatus;
import lombok.Data;

@Data
public class MemberDTO {
    private Long id;
    private String name;
    private String department;
    private int studentId;
    private String status;
    private String memberImageURL;
}