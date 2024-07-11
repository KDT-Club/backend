package com.ac.su.community.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts/{memberId}")
    public List<Post> getPostsByMemberId(@PathVariable Long memberId) {
        return postService.getPostsByMemberId(memberId);
    }
}
