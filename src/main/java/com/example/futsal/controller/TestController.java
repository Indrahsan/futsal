package com.example.futsal.controller;

import com.example.futsal.model.User;
import com.example.futsal.payload.request.LoginRequest;
import com.example.futsal.security.service.UserDetailsServiceImpl;
import com.example.futsal.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    Global global;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;


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

//    @GetMapping(value ="/consumeApi")
//    public ResponseEntity<?> consume() {
//        String username = "webgodigi";
//        String password = "";
//        String url = "https://godigi.co.id:8080/contents";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(username, password);
//
//        HttpEntity<String> request = new HttpEntity<String>(headers);
//        ResponseEntity<Map> response = restTemplateBuilder.build().exchange(url, HttpMethod.GET, request, Map.class);
//
//        return global.Response(response.getBody().get("data"), 0, "Berhasil consume api");
//
//    }
}
