package com.springboot.blog.service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        Post post = Post.builder()
                .id(1L)
                .title("Post Title")
                .description("Post Description")
                .content("Post Content")
                .user(new User())
                .createdDate(new Date())
                .build();

//        Mockito.when(postRepository.findById(1L).orElseThrow()).thenReturn(post);
        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));

    }

    @Test
    @DisplayName("Get Data base on Valid Post Id")
    public void whenValidPostId_thenPostShouldFound(){

        Long postId = 1L;

        PostDto found = postService.getPostById(postId);

        assertEquals(postId, found.getId());

    }
}