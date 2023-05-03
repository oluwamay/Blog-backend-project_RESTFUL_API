package com.invogue_fashionblog.repositories;

import com.invogue_fashionblog.entities.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileAttachment, String> {
}
