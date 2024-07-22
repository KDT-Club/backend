package com.ac.su.community.attachment;

import com.ac.su.community.post.Post;
import com.ac.su.community.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private PostRepository postRepository;

    public AttachmentDTO saveAttachment(AttachmentDTO attachmentDTO) {
        Optional<Post> optionalPost = postRepository.findById(attachmentDTO.getPostId());
        if (optionalPost.isPresent()) {
            Attachment attachment = new Attachment();
            attachment.setAttachmentName(attachmentDTO.getAttachmentName());
            attachment.setPost(optionalPost.get());
            Attachment savedAttachment = (Attachment) attachmentRepository.save(attachment);
            return new AttachmentDTO(savedAttachment.getId(), savedAttachment.getAttachmentName(), savedAttachment.getPost().getId());
        }
        throw new IllegalArgumentException("Invalid Post ID");
    }
}
