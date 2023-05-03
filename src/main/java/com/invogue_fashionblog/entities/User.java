package com.invogue_fashionblog.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Table(name="users")
public class User extends AuditableEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "user_id_sequence")
    @SequenceGenerator(name="user_id_sequence", sequenceName = "user_id_sequence", allocationSize = 1)
    @Column(name = "user_id")
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String salt;

    private String password;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> post = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Likes> likes = new ArrayList<>();

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salt = BCrypt.gensalt();
        log.info("salt:"+ salt);
        this.password = BCrypt.hashpw(password, salt);
    }

    public boolean validatePassword(String passwordDto, String passwordDB){
        return BCrypt.checkpw(passwordDto, passwordDB);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
