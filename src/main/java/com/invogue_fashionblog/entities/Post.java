package com.invogue_fashionblog.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends AuditableEntity implements Serializable {
    @Id
    @Column(name="post_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 25000)
    private String content;

    @OneToOne(fetch = FetchType.LAZY, cascade= {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name= "file_id", referencedColumnName = "id")
    private FileAttachment fileAttachment;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade= CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Likes> likesList = new ArrayList<>();

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void addLike(Likes like){likesList.add(like);}


}
