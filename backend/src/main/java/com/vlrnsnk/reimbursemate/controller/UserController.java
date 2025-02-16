package com.vlrnsnk.reimbursemate.controller;

import com.vlrnsnk.reimbursemate.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok().build();
    }

}
