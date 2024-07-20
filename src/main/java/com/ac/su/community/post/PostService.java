package com.ac.su.community.post;

import com.ac.su.community.attachment.AttachmentFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

  @Autowired
  private PostRepository postRepository;

  public List<Post> getPostsByMemberId(Long memberId) {

      return postRepository.findByMemberId(memberId);
    }

    public boolean deletePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            postRepository.deleteById(postId);
            return true;
        }
        return false;
    }

    public PostResponseDto updatePost(Long postId, PostUpdateDto postUpdateDto) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setTitle(postUpdateDto.getTitle());
            post.setContent(postUpdateDto.getContent());
            if (postUpdateDto.getAttachment_flag() != null && !postUpdateDto.getAttachment_flag().isEmpty()) {
                try {
                    post.setAttachmentFlag(AttachmentFlag.valueOf(postUpdateDto.getAttachment_flag()));
                } catch (IllegalArgumentException e) {
                    return new PostResponseDto("에러남: Invalid AttachmentFlag value");

            }
            }
            postRepository.save(post);
            return new PostResponseDto("성공");
        }
        return new PostResponseDto("에러남: Post not found");
    }

}

