package com.invogue_fashionblog.dto.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;

    @NotBlank(message = "title cannot be blank")
    private String title;

    @NotBlank(message = "content cannot be empty")
    private String content;

}
