package com.ac.su.community.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/members/{memberId}")
    public List<Post> getPostsByMemberId(@RequestParam Long id) {
        return postService.getPostsByMemberId(id);
    }
}