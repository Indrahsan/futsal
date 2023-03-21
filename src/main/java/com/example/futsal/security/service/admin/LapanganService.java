package com.example.futsal.security.service.admin;

import com.example.futsal.model.Lapangan;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Map;

public interface LapanganService {
    public ResponseEntity<Map> CreateLap(Lapangan lapangan, Principal principal);
    public ResponseEntity<Map> DelLap(Long idLap, Principal principal);
    public ResponseEntity<Map> EditLap(Long idLap, Principal principal, Lapangan obj);
}
