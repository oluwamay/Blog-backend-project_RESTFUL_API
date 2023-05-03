package com.invogue_fashionblog.services.serviceimpl.serviceimpl;

import com.invogue_fashionblog.dto.responses.UploadResponse;
import com.invogue_fashionblog.entities.FileAttachment;
import com.invogue_fashionblog.entities.Post;
import com.invogue_fashionblog.entities.User;
import com.invogue_fashionblog.exceptions.FashionBlogApiException;
import com.invogue_fashionblog.exceptions.ResourceNotFoundException;
import com.invogue_fashionblog.repositories.FileRepository;
import com.invogue_fashionblog.repositories.PostRepository;
import com.invogue_fashionblog.repositories.UserRepository;
import com.invogue_fashionblog.services.serviceimpl.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final PostRepository postRepository;
    @Override
    public UploadResponse saveFile(MultipartFile file, Long postId, Long userId) {

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId.toString()));
        if(userId != post.getUser().getId()){throw new FashionBlogApiException(HttpStatus.FORBIDDEN, "This post did not originate from user with id " + userId);
        }
        if(post.getFileAttachment() != null){
            throw new FashionBlogApiException(HttpStatus.BAD_REQUEST, "Post already has a file attachment");
        }
        String fileName = StringUtils.cleanPath((file.getOriginalFilename()));
        try{
            if(fileName.contains("..")){
                throw new FashionBlogApiException(HttpStatus.BAD_REQUEST, "FileName contains invalid characters");
            }

            FileAttachment attachment = FileAttachment.builder()
                    .fileName(fileName)
                    .fileSize(file.getSize())
                    .fileType(file.getContentType())
                    .data(file.getBytes()).build();

            FileAttachment attachment1 = fileRepository.save(attachment);

            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/v1/fashionblog/post/"+postId+"/download/")
            .path(attachment1.getId())
            .toUriString();

            attachment1.setDownloadURL(downloadUri);

            post.setFileAttachment(attachment1);

            return mapToResponse(attachment1, postId);
        } catch (IOException e) {
            throw new RuntimeException("Could not save file: "+ fileName);
        }
    }

    @Override
    public FileAttachment getFile(Long postId, String id) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId.toString()));
        if(!post.getFileAttachment().getId().equals(id)){
            throw new FashionBlogApiException(HttpStatus.FORBIDDEN, "File is not attached to this post");
        }
        return fileRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("File", "File Attachment", id));
    }

    @Override
    public void delete(String fileId, Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId.toString()));
        if (userId != post.getUser().getId()) {
            throw new FashionBlogApiException(HttpStatus.FORBIDDEN, "This post did not originate from user with id: " + userId);
        }
        FileAttachment file = fileRepository.findById(fileId).orElseThrow(()-> new ResourceNotFoundException("File","File_Id",fileId));
        if (fileId != file.getId() && fileId != post.getFileAttachment().getId()) {
            throw new FashionBlogApiException(HttpStatus.FORBIDDEN, "This file does not belong to post with post_id: " + postId);
        }

        fileRepository.deleteById(fileId);
    }

    public UploadResponse mapToResponse(FileAttachment attachment, Long post_id){
        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/fashionblog/post/"+post_id+"/download/")
                .path(attachment.getId())
                .toUriString();
        return
                UploadResponse.builder()
                        .fileSize(attachment.getFileSize())
                        .fileName(attachment.getFileName())
                        .downloadURL(downloadUri)
                        .fileType(attachment.getFileType()).build();

    }

}
