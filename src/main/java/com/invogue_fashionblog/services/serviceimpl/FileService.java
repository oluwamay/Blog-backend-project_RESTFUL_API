package com.invogue_fashionblog.services.serviceimpl;

import com.invogue_fashionblog.dto.responses.UploadResponse;
import com.invogue_fashionblog.entities.FileAttachment;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    UploadResponse saveFile(MultipartFile file, Long postId, Long userId);

    FileAttachment getFile(Long postId, String id);

    void delete(String fileId, Long postId, Long userId);
    UploadResponse mapToResponse(FileAttachment attachment, Long post_id);
}
