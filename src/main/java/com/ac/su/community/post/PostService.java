package com.ac.su.community.post;

import com.ac.su.community.attachment.Attachment;
import com.ac.su.community.attachment.AttachmentFlag;
import com.ac.su.community.attachment.AttachmentRepository;
import com.ac.su.community.board.Board;
import com.ac.su.community.board.BoardRepository;
import com.ac.su.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final AttachmentRepository attachmentRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.upload-url}")
    private String uploadUrl;

    public void createPost(PostDTO postDTO, MultipartFile[] files, Member member, Long boardId, Long clubId) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setMember(member);

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board Id: " + boardId));
        post.setBoardId(board);

        post.setAttachmentFlag(AttachmentFlag.valueOf(postDTO.getAttachmentFlag()));

        // Post를 먼저 저장
        Post savedPost = postRepository.save(post);

        // 첨부 파일이 있는 경우 Attachment 엔티티 생성 및 저장
        if ("Y".equals(postDTO.getAttachmentFlag()) && files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        Path uploadPath = Paths.get(uploadDir);
                        if (!Files.exists(uploadPath)) {
                            Files.createDirectories(uploadPath);
                        }

                        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                        Path path = uploadPath.resolve(fileName);
                        Files.write(path, file.getBytes());

                        Attachment newAttachment = new Attachment();
                        newAttachment.setAttachmentName(uploadUrl + fileName); // URL로 저장
                        newAttachment.setPostId(savedPost);

                        attachmentRepository.save(newAttachment);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException("File upload error", e);
                    }
                }
            }
        }
    }

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
