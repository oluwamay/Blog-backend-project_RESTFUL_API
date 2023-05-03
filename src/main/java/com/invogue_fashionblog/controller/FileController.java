package com.invogue_fashionblog.controller;

import com.invogue_fashionblog.dto.responses.UploadResponse;
import com.invogue_fashionblog.entities.FileAttachment;
import com.invogue_fashionblog.services.serviceimpl.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/fashionblog/post/{post_id}")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/file_upload/{user_id}")
    public ResponseEntity<UploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @PathVariable("post_id") Long post_id,
            @PathVariable("user_id") Long user_id
    ){
        UploadResponse attachment = fileService.saveFile(file, post_id, user_id);

//        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//            .path("/api/v1/fashionblog/post/"+post_id+"/download/")
//            .path(attachment.getId())
//            .toUriString();

    return new ResponseEntity<>(attachment, HttpStatus.CREATED);
    }

    @GetMapping("/download/{file_id}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable("file_id") String id,
            @PathVariable("post_id") Long post_id
    ){
    FileAttachment file = fileService.getFile(post_id, id);
    return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getFileType()))
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    "file: filename=\"" + file.getFileName()+"\"")
            .body(new ByteArrayResource(file.getData()));
    }

    @GetMapping("/delete_file/{file_id}/{user_id}")
    public ResponseEntity<String> deleteFile(
            @PathVariable("file_id") String file_id,
            @PathVariable("post_id") Long post_id,
            @PathVariable("user_id") Long user_id
    ){
        fileService.delete(file_id, post_id, user_id);
       return ResponseEntity.ok("File deleted successfully");
    }
}
