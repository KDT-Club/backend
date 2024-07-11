package com.ac.su.community.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

  @Autowired
  private PostRepository postRepository;

  public List<Post> getPostsByMemberId(Long memberId) {

      return postRepository.findByMemberId(memberId);
  }
}

