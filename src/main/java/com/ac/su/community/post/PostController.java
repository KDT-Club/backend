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
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @GetMapping("/posts/{memberId}")
    public List<Post> getPostsByMemberId(@PathVariable Long memberId) {
        return postService.getPostsByMemberId(memberId);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        boolean isDeleted = postService.deletePost(postId);
        if (isDeleted) {
            return ResponseEntity.ok("{\"message\":\"성공\"}");
        } else {
            return ResponseEntity.status(400).body("{\"message\":\"에러남\"}");
        }
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostUpdateDto postUpdateDto) {
        Optional<Post> updatedPost = postService.updatePost(postId, postUpdateDto);
        if (updatedPost.isPresent()) {
            Post post = updatedPost.get();
            return ResponseEntity.ok("{\"postId\":\"" + post.getId() + "\","
                    + "\"title\":\"" + post.getTitle() + "\","
                    + "\"content\":\"" + post.getContent() + "\","
                    + "\"attachment_flag\":\"" + post.getAttachmentFlag() + "\","
                    + "\"attachment_name\":\"" + postUpdateDto.getAttachmentName() + "\"}");
        } else {
            return ResponseEntity.status(400).body("{\"message\":\"에러남\"}");
        }
    }

}
