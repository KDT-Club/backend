package com.ac.su.community.attachment;

import com.ac.su.community.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByPostId(Post post);
}
