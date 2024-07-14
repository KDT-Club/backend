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
        post.setBoard(board);

        if (boardId == 2 || boardId == 3) {
            if (member.getManagedClub() == null || !member.getManagedClub().getId().equals(clubId)) {
                throw new IllegalArgumentException("You do not have permission to create a post in this club.");
            }
            post.setClubName(member.getManagedClub().getName());
        } else if (boardId == 4) {
            boolean isMember = member.getJoinedClubs().stream().anyMatch(club -> club.getId().equals(clubId));
            if (!isMember) {
                throw new IllegalArgumentException("You do not have permission to create a post in this club.");
            }
            post.setClubName(member.getJoinedClubs().stream().filter(club -> club.getId().equals(clubId)).findFirst().map(club -> club.getClub().getName()).orElseThrow(() -> new IllegalArgumentException("Club not found")));
        }

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
                        newAttachment.setPost(savedPost);

                        attachmentRepository.save(newAttachment);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException("File upload error", e);
                    }
                }
            }
        }
    }
}
