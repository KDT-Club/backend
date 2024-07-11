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

    public void createPost(PostDTO postDTO, Member member, Long boardId) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setMember(member);

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("Invalid board Id: " + boardId));
        post.setBoard(board);

        if (boardId == 3) {
            post.setClubName(member.getManagedClub() != null ? member.getManagedClub().getName() : "");
        }


        post.setAttachmentFlag(postDTO.getAttachmentFlag());

        // Post를 먼저 저장
        Post savedPost = postRepository.save(post);

        // 첨부 파일이 있는 경우 Attachment 엔티티 생성 및 저장
        if (postDTO.getAttachmentFlag() == AttachmentFlag.Y && postDTO.getAttachments() != null) {
            for (MultipartFile attachment : postDTO.getAttachments()) {
                if (!attachment.isEmpty()) {
                    try {
                        Path uploadPath = Paths.get(uploadDir);
                        if (!Files.exists(uploadPath)) {
                            Files.createDirectories(uploadPath);
                        }

                        String fileName = System.currentTimeMillis() + "_" + attachment.getOriginalFilename();
                        Path path = uploadPath.resolve(fileName);
                        Files.write(path, attachment.getBytes());

                        Attachment newAttachment = new Attachment();
                        newAttachment.setAttachmentName(uploadUrl + fileName); // URL로 저장
                        newAttachment.setPost(savedPost);

                        attachmentRepository.save(newAttachment);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void createClubPost(PostDTO postDTO, Member member, Long boardId, Long clubId) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setMember(member);

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("Invalid board Id: " + boardId));
        post.setBoard(board);


        post.setAttachmentFlag(postDTO.getAttachmentFlag());
        post.setClubName(member.getManagedClub().getName());

        // Post를 먼저 저장
        Post savedPost = postRepository.save(post);

        // 첨부 파일이 있는 경우 Attachment 엔티티 생성 및 저장
        if (postDTO.getAttachmentFlag() == AttachmentFlag.Y && postDTO.getAttachments() != null) {
            for (MultipartFile attachment : postDTO.getAttachments()) {
                if (!attachment.isEmpty()) {
                    try {
                        Path uploadPath = Paths.get(uploadDir);
                        if (!Files.exists(uploadPath)) {
                            Files.createDirectories(uploadPath);
                        }

                        String fileName = System.currentTimeMillis() + "_" + attachment.getOriginalFilename();
                        Path path = uploadPath.resolve(fileName);
                        Files.write(path, attachment.getBytes());

                        Attachment newAttachment = new Attachment();
                        newAttachment.setAttachmentName(uploadUrl + fileName); // URL로 저장
                        newAttachment.setPost(savedPost);

                        attachmentRepository.save(newAttachment);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
