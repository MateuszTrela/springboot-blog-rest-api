package com.springboot.blog.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {
    private long id;
//    @NotEmpty(message = "Name should not be null or empty")
//    private String name;
//    @NotEmpty(message = "Email should not be null or empty")
//    @Email
//    private String email;
    @NotEmpty
    @Size(min = 10, message = "Comment body should have at least 10 characters")
    private String body;

    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Paris")
    private Date createdDate;
}
