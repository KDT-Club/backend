package com.ac.su.community.post;

import com.ac.su.community.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByBoardId(Board boardId);
    List<Post> findByBoardIdAndClubName(Board board, String clubName);
}