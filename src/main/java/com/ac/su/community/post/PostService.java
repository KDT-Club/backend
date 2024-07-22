package com.ac.su.community.post;

import com.ac.su.community.attachment.Attachment;
import com.ac.su.community.attachment.AttachmentDTO;
import com.ac.su.community.attachment.AttachmentFlag;
import com.ac.su.community.attachment.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

  @Autowired
  private PostRepository postRepository;
  @Autowired
  private AttachmentService attachmentService;

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
            // attachment 코드 추가
            if (postUpdateDto.getAttachment_flag() != null && !postUpdateDto.getAttachment_flag().isEmpty() && !postUpdateDto.getAttachment_flag().equals("N")) {
                List<String> attachmentNames;
                if (postUpdateDto.getAttachment_name() != null && !postUpdateDto.getAttachment_name().isEmpty()) {
                    // 단일 문자열을 리스트로 변환
                    attachmentNames = Collections.singletonList(postUpdateDto.getAttachment_name());
                } else {
                    attachmentNames = Collections.emptyList();
                }
                if (!attachmentNames.isEmpty()) {
                    for (String attachmentName : attachmentNames) {
                        AttachmentDTO attachmentDTO = new AttachmentDTO(null, attachmentName, post.getId());
                        attachmentService.saveAttachment(attachmentDTO);
                    }
                }
            }
            return new PostResponseDto("성공");
        }
        return new PostResponseDto("에러남: Post not found");
    }

}

