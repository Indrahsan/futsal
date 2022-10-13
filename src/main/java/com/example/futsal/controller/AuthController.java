package com.example.futsal.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.futsal.model.ERole;
import com.example.futsal.model.Role;
import com.example.futsal.model.User;
import com.example.futsal.payload.request.LoginRequest;
import com.example.futsal.payload.request.SignupRequest;
import com.example.futsal.payload.response.JwtResponse;
import com.example.futsal.payload.response.MessageResponse;
import com.example.futsal.repository.RoleRepository;
import com.example.futsal.repository.UserRepository;
import com.example.futsal.security.jwt.JwtUtils;
import com.example.futsal.security.service.UserDetailsImpl;
import com.example.futsal.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    Global global;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;



    @PostMapping(value ="/signin", consumes = {"application/xml","application/json"})
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {

            Map<String, Object> map = new LinkedHashMap<>();

           Boolean check = userRepository.existsByUsername(loginRequest.getUsername());
           User data = userRepository.findOneByUsername(loginRequest.getUsername());

            if (!(encoder.matches(loginRequest.getPassword(), data.getPassword()))) {
                return global.Response(null, 21, "Wrong Password!");
            }

           if (check == true && (encoder.matches(loginRequest.getPassword(), data.getPassword()))) {

               Authentication authentication = authenticationManager.authenticate(
                       new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

               SecurityContextHolder.getContext().setAuthentication(authentication);
               String jwt = jwtUtils.generateJwtToken(authentication);

               UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
               List<String> roles = userDetails.getAuthorities().stream()
                       .map(item -> item.getAuthority())
                       .collect(Collectors.toList());

               map.put("user", data);
               map.put("accessToken", jwt);
               map.put("tokenType", "Bearer ");
               return global.Response(map, 0, "Succes!");
           } else {
               return global.Response(null, 20, "Data not found! ");
           }

        } catch (Exception e) {
            return global.Response(e, 500, "internal server error");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getPhonenum(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        String url = "http://localhost:8080/api/auth/signin?username=" + user.getUsername() +
                "&password=" + user.getPassword();
        ResponseEntity<Map> response = restTemplateBuilder.build().postForEntity(url, signUpRequest, Map.class);


        return global.Response(response.getBody().get("data"), 0, "User registered successfully!");
    }
}

