package com.ac.su.member;

import lombok.Data;

@Data
public class MemberDTO {
    private Long id;
    private String name;
    private String department;
    private int studentId;
    private String password;
    private int phone;
    private String memberImageURL;

}