package com.invogue_fashionblog.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileAttachment extends AuditableEntity{

    @Id
    @GeneratedValue(generator= "uuid")
    @GenericGenerator(name="uuid", strategy="uuid2")
    private String id;

    private String fileName;

    private String fileType;

    private Long fileSize;

    private String downloadURL;

    @Lob
    @Column(length= 52428800)
    private byte[] data;

}
