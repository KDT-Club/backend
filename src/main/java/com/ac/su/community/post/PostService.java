package com.ac.su.community.post;

import com.ac.su.community.attachment.AttachmentFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<Post> updatePost(Long postId, PostUpdateDto postUpdateDto) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setTitle(postUpdateDto.getTitle());
            post.setContent(postUpdateDto.getContent());
            if (postUpdateDto.getAttachmentFlag() != null) {
                try {
                    post.setAttachmentFlag(AttachmentFlag.valueOf(postUpdateDto.getAttachmentFlag()));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid AttachmentFlag value: " + postUpdateDto.getAttachmentFlag());
                }
            } else {
                post.setAttachmentFlag(AttachmentFlag.N); // 기본 값 설정, 필요시 다른 기본 값으로 변경
            }
            postRepository.save(post);
            return Optional.of(post);
        }
        return Optional.empty();
    }
}

