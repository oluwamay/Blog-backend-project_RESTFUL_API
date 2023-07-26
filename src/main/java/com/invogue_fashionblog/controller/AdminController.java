package com.invogue_fashionblog.controller;

import com.invogue_fashionblog.services.serviceimpl.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/fashionblog/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final UserService userService;

    @DeleteMapping("/deactivate/{user_id}")
    public ResponseEntity<String> deactivateUserAccount(@PathVariable Long user_id){
        log.info("deactivate endpoint hit");
        return userService.deactivateAccount(user_id);
    }

}
