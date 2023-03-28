package com.example.futsal.controller.user;

import com.example.futsal.model.Booked;
import com.example.futsal.model.Lapangan;
import com.example.futsal.security.service.user.BookedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BookedController {

    @Autowired
    BookedService bookedService;

    @PostMapping("/booked/{id_lapangan}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map> add (@PathVariable(value = "id_lapangan") Long id_lapangan ,@Valid @RequestBody Booked booked, Principal principal) {
        return bookedService.BookedLap(id_lapangan, principal, booked);
    }
}
