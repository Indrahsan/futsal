package com.example.futsal.controller.admin;

import com.example.futsal.model.Lapangan;
import com.example.futsal.security.service.admin.LapanganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/lapangan")
public class LapanganController {
    @Autowired
    LapanganService lapanganService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map> add (@Valid @RequestBody Lapangan lapangan, Principal principal) {
        return lapanganService.CreateLap(lapangan, principal);
    }

    @DeleteMapping("/delete/{id_lapangan}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map> delete(@PathVariable(value = "id_lapangan") Long id_lapangan, Principal principal) {
        return lapanganService.DelLap(id_lapangan, principal);
    }

    @PutMapping("/edit/{id_lapangan}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map> edit (@PathVariable(value = "id_lapangan") Long id_lapangan, Principal principal, @Valid @RequestBody Lapangan obj) {
        return lapanganService.EditLap(id_lapangan, principal, obj);
    }
}
