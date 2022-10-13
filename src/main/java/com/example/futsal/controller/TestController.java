package com.example.futsal.controller;

import com.example.futsal.model.User;
import com.example.futsal.payload.request.LoginRequest;
import com.example.futsal.security.service.UserDetailsServiceImpl;
import com.example.futsal.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    Global global;

    @Autowired
    UserDetailsServiceImpl userDetailsService;


    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @PostMapping(value ="/getProfile")
    public ResponseEntity<?> test(Principal principal) {
        User user = global.getUserIdToken(principal, userDetailsService);

        return global.Response(user, 0, "Berhasil");

    }
}
