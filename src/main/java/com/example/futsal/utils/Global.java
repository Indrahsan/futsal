package com.example.futsal.utils;

import com.example.futsal.model.User;
import com.example.futsal.repository.UserRepository;
import com.example.futsal.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class Global {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<Map> Response(Object objek, int code, String info){
        Map map = new LinkedHashMap<>();
        map.put("code", code);
        map.put("info", info);
        map.put("data", objek);

        if (code == 0 || code == 20 || code == 21) {
            return new ResponseEntity<Map>(map, HttpStatus.OK);
        } else if(code == 500) {
            return new ResponseEntity<Map>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }else if(code == 401) {
            return new ResponseEntity<Map>(map, HttpStatus.UNAUTHORIZED);
        }
        else {
            return new ResponseEntity<Map>(map, HttpStatus.BAD_REQUEST);
        }
    }

    public User getUserIdToken(Principal principal, UserDetailsServiceImpl userDetailsService) {
        UserDetails user = null;
        String username = principal.getName();
        if (!StringUtils.isEmpty(username)) {
            user = userDetailsService.loadUserByUsername(username);
        }

        if (null == user) {
            throw new UsernameNotFoundException("User not found");
        }
        User idUser = userRepository.findOneByUsername(user.getUsername());
        if (null == idUser) {
            throw new UsernameNotFoundException("User name not found");
        }
        return idUser;
    }
}
