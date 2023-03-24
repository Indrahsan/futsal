package com.example.futsal.security.service.user;

import com.example.futsal.model.Booked;
import com.example.futsal.model.Lapangan;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Map;

public interface BookedService {
    public ResponseEntity<Map> BookedLap(Long idLap, Principal principal, Booked obj);
}
