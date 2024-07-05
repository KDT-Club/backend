package com.ac.su.clubmember;

import com.ac.su.member.MemberService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClubMemberDTO {
    private String memberImageurl;
    private String name;
    private MemberStatus status;

    public static ClubMemberDTO toClubMemberDTO(ClubMember clubMember){
        ClubMemberDTO clubMemberDTO = new ClubMemberDTO();
        clubMemberDTO.setMemberImageurl(clubMember.getMember().getMemberImageURL());
        clubMemberDTO.setName(clubMember.getMember().getName());
        clubMemberDTO.setStatus(clubMember.getMember().getStatus());

        return clubMemberDTO;
    }
}
