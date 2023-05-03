package com.invogue_fashionblog.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadResponse {
    private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;
}
