package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {

        User user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();
        userRepository.save(user); // Save the User entity

        Post post = Post.builder()
                .id(1L)
                .title("Title")
                .description("Description")
                .content("Content")
                .createdDate(new Date())
                .user(user)
                .build();

        postRepository.save(post);
    }

    @Test
    public void whenFindById_thenReturnPost(){
        Post post = postRepository.findById(1L).get();
        assertEquals(post.getTitle(), "Title");
    }
}