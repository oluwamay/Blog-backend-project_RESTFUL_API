package com.invogue_fashionblog.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="users")
public class User extends AuditableEntity{
    @Id
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    @SequenceGenerator(
            name="user_id_sequence",
            sequenceName = "user_id_sequence",
            allocationSize = 1
    )
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;



}
