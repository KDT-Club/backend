package com.ac.su.community.post;

import com.ac.su.community.attachment.Attachment;
import com.ac.su.community.attachment.AttachmentFlag;
import com.ac.su.community.attachment.AttachmentRepository;
import com.ac.su.community.board.Board;
import com.ac.su.community.board.BoardRepository;
import com.ac.su.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final AttachmentRepository attachmentRepository;

    private static final String UPLOADED_FOLDER = "uploads/";

    public void createPost(PostDTO postDTO, Member member, Long boardId) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setMember(member);

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("Invalid board Id: " + boardId));
        post.setBoard(board);

        // boardId에 따른 PostType 설정
        String postType;
        switch (boardId.intValue()) {
            case 1:
                postType = "COMMUNITY";
                break;
            case 2:
                postType = "CLUB_NOTICE";
                break;
            case 3:
                postType = "CLUB_ACTIVITY";
                break;
            case 4:
                postType = "CLUB_INTERNAL";
                break;
            default:
                throw new IllegalArgumentException("Invalid board Id: " + boardId);
        }
        post.setPostType(postType);

        post.setAttachmentFlag(postDTO.getAttachmentFlag());

        if (postDTO.getAttachmentFlag() == AttachmentFlag.Y) {
            MultipartFile attachment = postDTO.getAttachment();
            if (attachment != null && !attachment.isEmpty()) {
                try {
                    Path uploadPath = Paths.get(UPLOADED_FOLDER);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    byte[] bytes = attachment.getBytes();
                    Path path = uploadPath.resolve(attachment.getOriginalFilename());
                    Files.write(path, bytes);

                    post.setAttachmentName(attachment.getOriginalFilename());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Post를 먼저 저장
        Post savedPost = postRepository.save(post);

        // 첨부 파일이 있는 경우 Attachment 엔티티 생성 및 저장
        if (postDTO.getAttachmentFlag() == AttachmentFlag.Y && postDTO.getAttachment() != null && !postDTO.getAttachment().isEmpty()) {
            Attachment newAttachment = new Attachment();
            newAttachment.setAttachmentName(postDTO.getAttachment().getOriginalFilename());
            newAttachment.setPost(savedPost);

            attachmentRepository.save(newAttachment);
        }
    }
}
