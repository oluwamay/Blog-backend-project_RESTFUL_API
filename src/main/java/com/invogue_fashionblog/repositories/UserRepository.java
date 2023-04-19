package com.invogue_fashionblog.repositories;

import com.invogue_fashionblog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
