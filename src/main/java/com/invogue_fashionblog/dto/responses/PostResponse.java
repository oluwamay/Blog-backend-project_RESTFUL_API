package com.invogue_fashionblog.dto.responses;

import com.invogue_fashionblog.entities.Comment;
import com.invogue_fashionblog.entities.FileAttachment;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;

    private String title;

    private String content;

    private Long Like_count;

    private UploadResponse file;

    private Set<CommentResponse> comments;
}
