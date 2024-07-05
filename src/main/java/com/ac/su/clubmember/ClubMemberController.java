package com.ac.su.clubmember;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubMemberController {
    private final ClubMemberService clubMemberService;

    // 동아리 회원 리스트 출력
    @GetMapping("/clubMembers")
    public String findAll(Model model) {
        List<ClubMemberDTO> clubMemberDTOList = clubMemberService.findAll();
        model.addAttribute("clubMemberList", clubMemberDTOList);
        return "club_member_list";
    }

//    @GetMapping("/make-dummyClubMember")
//    public ResponseEntity<List<ClubMember>> makeDummyClubData(@RequestParam("count") int count) {
//        // count 값이 1 이상 100 이하가 되도록 제약조건 추가하기!
//        if(count<1 || count>100) {
//            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
//        }
//        List<ClubMember> clubMembers = clubMemberService.makeDummyClubMemberData(count);
//        return new ResponseEntity<>(clubMembers, HttpStatus.OK);
//    }
}
