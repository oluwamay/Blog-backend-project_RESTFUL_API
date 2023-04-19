package com.invogue_fashionblog.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends AuditableEntity{
    @Id
    @Column(name="post_id")
    private Long id;

    private String title;

    private String content;



}
