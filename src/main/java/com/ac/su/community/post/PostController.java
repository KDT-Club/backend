package com.ac.su.community.post;


import com.ac.su.community.board.Board;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/board/1/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @GetMapping("/posts/{memberId}")
    public List<Post> getPostsByMemberId(@PathVariable Long memberId) {
        return postService.getPostsByMemberId(memberId);
    }

}
