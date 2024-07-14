package com.ac.su.security;

import com.ac.su.member.MemberRepository;
import com.ac.su.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        int studentId = Integer.parseInt(username);
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid user"));

        return User.withDefaultPasswordEncoder()
                .username(String.valueOf(member.getStudentId()))
                .password(member.getPassword())
                .roles("USER")
                .build();
    }
}
