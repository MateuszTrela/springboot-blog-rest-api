package com.springboot.blog.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class PostDto {
    private long id;

    @NotEmpty(message = "Post title cannot be empty")
    @Size(min = 2, max = 255, message = "Post title should have 2 to 255 characters")
    private String title;

    @NotEmpty(message = "Post description cannot be empty")
    @Size(min = 10, max = 1000, message = "Post description should have 10 to 100 characters")
    private String description;

    @NotEmpty(message = "Post content cannot be empty")
    @Size(max = 10000, message = "Post content should have up to 10000 characters")
    private String content;

    private String imageUrl;

    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Paris")
    private Date createdDate;

    private Set<CommentDto> comments;
}
