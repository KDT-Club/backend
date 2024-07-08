package com.ac.su.member;

import com.ac.su.clubmember.MemberStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustonUser extends User {
    private Long id;
    private String department;
    private MemberStatus status;
    private String memberImageURL;
    private String name;

    public CustonUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
