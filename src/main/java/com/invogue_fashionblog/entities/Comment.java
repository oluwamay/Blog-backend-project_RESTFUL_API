package com.invogue_fashionblog.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends AuditableEntity{
   @Id
   @GeneratedValue (strategy= GenerationType.IDENTITY)
   private Long id;

   private String comment;

   @ManyToOne()
   @JoinColumn(name="post_id", referencedColumnName = "post_id", nullable = false)
   private Post post;

   @ManyToOne()
   @JoinColumn(name="user_id", referencedColumnName = "user_id")
   private User user;


}
