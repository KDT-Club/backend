package com.ac.su.community.board;

import com.ac.su.community.club.Club;
import com.ac.su.community.club.ClubRepository;
import com.ac.su.community.post.Post;
import com.ac.su.community.post.PostRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final PostRepository postRepository; //post 과련 DB 입출력 함수
    private final ClubRepository clubRepository;

    // /board/{1}/posts - 진욱
    @GetMapping("/board/{board_id}/posts")
    public List<?> getAllGeneralPost(@PathVariable Long board_id) {
        //커뮤니티 자유게시판 글 모두 가져오기
        Board board = new Board();
        board.setId(board_id);
        var a = postRepository.findByBoardId(board);
        return a;
    }

@GetMapping("/clubs/{clubId}/board/2/posts")
public List<Post> getAllNoticePosts(@PathVariable Long clubId) {
    // 주어진 클럽 ID로 클럽 정보 가져오기
    Club club = clubRepository.findById(clubId)
            .orElseThrow(() -> new IllegalArgumentException("Club not found with id: " + clubId));

    // boardId가 2인 Board 객체 생성
    Board board = new Board();
    board.setId(2L);

    // 주어진 board와 clubName에 해당하는 post 검색
    List<Post> posts = postRepository.findByBoardIdAndClubName(board, club.getName());
    return posts;
    }

}
